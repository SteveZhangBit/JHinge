"""
This file convert a grammar represented in BNF into LL(1)
"""
import re

bnf = None
entry = None


def generate(file_name):
    global bnf
    global entry

    grammar_file = open(file_name)
    bnf = [BNF(line) for line in grammar_file.readlines()]
    entry = bnf[-1]
    print_bnf()

    # print '\nextend.......'
    # extend()
    # print_bnf()

    print '\nremove left recursion......'
    remove_all_left_recursion()
    print_bnf()

    # print '\nremove repeated item......'
    # remove_repeated_item()
    # print_bnf()

    print '\nremove unused sentence......'
    remove_unused_sentece()
    print_bnf()


class BNF:
    """docstring for BNF"""
    def __init__(self, statement=None):
        if statement is None:
            return

        pattern = re.compile(r'(\w+|<\w+>|"[^"]+"|\[[^\]]+\]'
                             r'|\([^\)]+\)[+*]?)|((?<!")[\|](?!"))')
        match = pattern.findall(statement)

        self.right, tmp = [], []
        for i in range(len(match)):
            m = match[i]
            if i == 0:
                self.left = m[0]
                continue
            if m[0] != '':
                tmp.append(m[0])
            else:
                self.right.append(tmp)
                tmp = []
        self.right.append(tmp)

    def extend(self):
        """BNF allow to use '[]', '()', '+', so we need to convert
        it to normal way."""
        global bnf

        postfix = 0     # use to generate new non-terminal name
        for s in self.right:    # s is a sentence (a python list)
            for i in s:     # i is a word right here
                # []
                if i[0] == '[' and i[-1] == ']':
                    b = BNF()
                    b.left = self.left + str(postfix)

                    index = s.index(i)
                    rest = s[s.index(i) + 1:]
                    rest.insert(0, i[1:-1])
                    b.right = [rest, rest[1:]]

                    # repace the rest part of the old sentence
                    # with the new non-terminal
                    s[index:] = [b.left]
                    bnf.append(b)

                    postfix += 1
                # ()*
                elif i[0] == '(' and i[-1] == '*':
                    # name ::= name sub |
                    name = self.left + str(postfix)
                    sub = i[:-1]
                    b = BNF(name + '::=' + name + ' ' + sub + '|')

                    s[s.index(i)] = name
                    bnf.append(b)

                    postfix += 1
                # ()+
                elif i[0] == '(' and i[-1] == '+':
                    name = self.left + str(postfix)
                    sub = i[:-1]
                    # name ::= name sub | sub
                    b = BNF(name + '::=' + name + ' ' + sub + '|' + sub)

                    s[s.index(i)] = name
                    bnf.append(b)

                    postfix += 1
                # ()
                elif i[0] == '(' and i[-1] == ')':
                    b = BNF(self.left + str(postfix) + '::=' + i[1:-1])
                    s[s.index(i)] = b.left
                    bnf.append(b)

                    postfix += 1

    def __str__(self):
        re = self.left + ' ::='
        for s in self.right:
            for i in s:
                re += ' ' + i
            if self.right.index(s) != len(self.right) - 1:
                re += ' |'
        return re


def print_bnf():
    global bnf

    for b in bnf:
        print bnf.index(b), ': ', b


# extend
def extend():
    global bnf

    while True:
        tmp = bnf[:]
        for b in tmp:
            b.extend()
        if len(bnf) == len(tmp):
            break


# remove repeated item
def replace_non_terminal(source, target):
    global bnf

    for b in bnf:
        for s in b.right:
            for i in range(len(s)):
                if s[i] == source:
                    s[i] = target


def remove_repeated_item():
    global bnf

    while True:
        remove_set = set()
        m = len(bnf)
        for i in range(m):
            for j in range(i + 1, m):
                if bnf[i].right == bnf[j].right:
                    replace_non_terminal(bnf[j].left, bnf[i].left)
                    remove_set.add(j)
        if len(remove_set) != 0:
            tmp = []
            for i in range(m):
                if i not in remove_set:
                    tmp.append(bnf[i])
            bnf = tmp
        else:
            break


# remove left recursion
def remove_direcr_left_recursion(b):
    global bnf

    # if b doesn't have left recusion,
    # there's no need to remove anything
    b1 = None
    for s in b.right[:]:
        if len(s) > 0 and s[0] == b.left:
            if b1 is None:
                b1 = BNF()
                b1.left = b.left + '_'
                b1.right = []

                bnf.append(b1)

            b.right.remove(s)
            rest = s[1:]
            rest.append(b1.left)
            b1.right.append(rest)

    if b1 is not None:
        b1.right.append([])

        if len(b.right) == 0:
            b.right.append([])
        for s in b.right:
            s.append(b1.left)


# remove all left recursion
def remove_all_left_recursion():
    global bnf

    for i in range(len(bnf)):
        tmp = []
        for s in bnf[i].right:
            flag = False
            for j in range(i):
                if len(s) > 0 and s[0] == bnf[j].left:
                    flag = True
                    for beta in bnf[j].right:
                        copy = beta[:]
                        copy.extend(s[1:])
                        tmp.append(copy)
                    break
            if not flag:
                tmp.append(s)

        bnf[i].right = tmp
        remove_direcr_left_recursion(bnf[i])


# remove unused sentence
def remove_unused_sentece():
    global bnf
    global entry

    tmp = {b.left: b.right for b in bnf}
    bnf_dic = {entry.left: entry.right}

    def walk_through(right):
        for s in right:
            for i in s:
                if i in tmp and i not in bnf_dic:
                    bnf_dic[i] = tmp[i]
                    walk_through(bnf_dic[i])
                elif (i[0] == '(' and i[-1] == ')') \
                        or (i[0] == '[' and i[-1] == ']'):
                    b = BNF('tmp::=' + i[1:-1])
                    walk_through(b.right)
                elif i[0] == '(' and (i[-1] == '+' or i[-1] == '*'):
                    b = BNF('tmp::=' + i[1:-2])
                    walk_through(b.right)

    walk_through(bnf_dic[entry.left])
    bnf = []
    for left, right in bnf_dic.iteritems():
        b = BNF()
        b.left = left
        b.right = right
        bnf.append(b)
