"""
This file convert a grammar represented in BNF into LL(1)
"""
import re

bnf = None
bnf_dic = None
entry = None
terminal_set = set()
non_terminal_set = set()
first_dic = {}
follow_dic = {}


def generate(file_name, entry_line=0):
    global bnf
    global entry

    BNF.postfix = 0
    remove_left_factor.postfix = 0

    grammar_file = open(file_name)
    bnf = [BNF(line) for line in grammar_file.readlines()]
    grammar_file.close()
    entry = bnf[entry_line]

    print '\nextend......'
    extend()
    print 'BNF length: %d' % len(bnf)

    print '\nremove repeated item......'
    remove_repeated_item()
    print 'BNF length: %d' % len(bnf)

    print '\nremove left recursion......'
    remove_all_left_recursion()
    print 'BNF length: %d' % len(bnf)

    print '\nremove left factoring......'
    remove_left_factor()
    print 'BNF length: %d' % len(bnf)

    print '\nremove repeated item......'
    remove_repeated_item()
    print 'BNF length: %d' % len(bnf)

    print '\nremove unused sentence......'
    remove_unused_sentece()
    print 'BNF length: %d' % len(bnf)

    print '\ncheck FIRST set......'
    collison = set()
    for b in bnf:
        b.first_set = []
        for s in b.right:
            b.first_set.append(first_for_stat(b.left, s))

        m = len(b.first_set)
        for i in range(m):
            for j in range(i + 1, m):
                if len(b.first_set[i].intersection(b.first_set[j])) != 0:
                    collison.add(b)
                    break

    print '\ncollisions:'
    for b in collison:
        print b.left

    # no collision
    if len(collison) == 0:
        out()


class BNF:
    """docstring for BNF"""
    postfix = 0     # use to generate new non-terminal name

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

        for s in self.right:    # s is a sentence (a python list)
            for i in s:     # i is a word right here
                # []
                if i[0] == '[' and i[-1] == ']':
                    # name ::= sub |
                    name = '_' + str(BNF.postfix) + '_extend'
                    sub = i[:-1]
                    b = BNF(name + '::=' + sub + ' |')

                    s[s.index(i)] = name
                    bnf.append(b)

                    BNF.postfix += 1
                # ()*
                elif i[0] == '(' and i[-1] == '*':
                    # name ::= name sub |
                    name = '_' + str(BNF.postfix) + '_extend'
                    sub = i[:-1]
                    b = BNF(name + '::=' + name + ' ' + sub + ' |')

                    s[s.index(i)] = name
                    bnf.append(b)

                    BNF.postfix += 1
                # ()+
                elif i[0] == '(' and i[-1] == '+':
                    name = '_' + str(BNF.postfix) + '_extend'
                    sub = i[:-1]
                    # name ::= name sub | sub
                    b = BNF(name + '::=' + name + ' ' + sub + ' | ' + sub)

                    s[s.index(i)] = name
                    bnf.append(b)

                    BNF.postfix += 1
                # ()
                elif i[0] == '(' and i[-1] == ')':
                    name = '_' + str(BNF.postfix) + '_extend'
                    b = BNF(name + '::=' + i[1:-1])
                    s[s.index(i)] = b.left
                    bnf.append(b)

                    BNF.postfix += 1

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
def remove_direct_left_recursion(b):
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
        while True:
            changed = False
            tmp = []

            for s in bnf[i].right:
                flag = False
                for j in range(i):
                    if len(s) > 0 and s[0] == bnf[j].left:
                        changed = flag = True

                        for alpha in bnf[j].right:
                            alpha = alpha[:]
                            alpha.extend(s[1:])
                            tmp.append(alpha)

                        break

                if not flag:
                    tmp.append(s)

            bnf[i].right = tmp
            if not changed:
                break

        remove_direct_left_recursion(bnf[i])


# remove unused sentence
def remove_unused_sentece():
    global bnf
    global entry
    global terminal_set
    global non_terminal_set
    global bnf_dic

    tmp = {b.left: b.right for b in bnf}
    bnf_dic = {entry.left: entry.right}
    # print entry

    def walk_through(right):
        for s in right:
            for i in s:
                if i in tmp and i not in bnf_dic:
                    # print i, '::=', tmp[i]
                    bnf_dic[i] = tmp[i]
                    walk_through(bnf_dic[i])
                elif i[0] == '<' or i[0] == '"':
                    terminal_set.add(i)

    walk_through(bnf_dic[entry.left])

    non_terminal_set = set(bnf_dic.keys())

    bnf = []
    for left, right in bnf_dic.iteritems():
        b = BNF()
        b.left = left
        b.right = right
        bnf.append(b)


def remove_left_factor():
    global bnf

    while True:
        tmp = bnf[:]

        for b in bnf:
            s_dic = {}
            for s in b.right:
                if len(s) == 0:
                    s_dic[''] = [[]]
                    continue

                if s[0] in s_dic:
                    s_dic[s[0]].append(s)
                else:
                    s_dic[s[0]] = [s]

            b.right = []
            for key, val in s_dic.iteritems():
                if len(val) > 1:    # exist left factoring
                    b_ = BNF()
                    b_.left = '_' + str(remove_left_factor.postfix)\
                        + '_leftfactor'
                    b_.right = [s[1:] for s in val]
                    bnf.append(b_)

                    b.right.append([key, b_.left])

                    remove_left_factor.postfix += 1
                else:
                    b.right.extend(val)

        if len(tmp) == len(bnf):
            break

remove_left_factor.postfix = 0


def first_for_stat(left, statement):
    global terminal_set
    global non_terminal_set

    if len(statement) == 0:
        return follow(left)
    else:
        word = statement[0]
        if word in terminal_set:
            return set([word])
        elif word in non_terminal_set:
            re = first(word)
            if '' in re:
                re = re.union(first_for_stat(left, statement[1:]))
                re.remove('')
                return re
            else:
                return re


def first(non_terminal):
    global bnf_dic
    global terminal_set
    global non_terminal_set
    global first_dic

    if non_terminal in first_dic:
        return first_dic[non_terminal]
    else:
        first_dic[non_terminal] = set()
        for s in bnf_dic[non_terminal]:
            if len(s) == 0:
                first_dic[non_terminal].add('')
            else:
                first_dic[non_terminal] = \
                    first_dic[non_terminal].union(
                        first_for_stat(non_terminal, s))

        return first_dic[non_terminal]


def follow(non_terminal):
    global bnf
    global entry
    global follow_dic

    if non_terminal in follow_dic:
        return follow_dic[non_terminal]
    else:
        if non_terminal == entry.left:
            follow_dic[non_terminal] = set(['<EOF>'])
        else:
            follow_dic[non_terminal] = set()
        for b in bnf:
            if b.left == non_terminal:
                continue
            for s in b.right:
                for i in range(len(s)):
                    if s[i] == non_terminal:
                        follow_dic[non_terminal] =\
                            follow_dic[non_terminal].union(
                                first_for_stat(b.left, s[i + 1:]))

        return follow_dic[non_terminal]


def out():
    global bnf
    global terminal_set
    global non_terminal_set
    global entry

    temp_file = open('parser-template.java')
    template = ''
    for line in temp_file.readlines():
        template += line
    temp_file.close()

    rule_pattern = re.compile(r'{%rules.*rules%}', re.S)
    rules_template = rule_pattern.findall(template)[0][7:-7]

    stmts_pattern = re.compile(r'{%statements.*statements%}', re.S)
    statements_template = stmts_pattern.findall(template)[0][12:-12]

    all_code = ''
    for b in bnf:
        code = rules_template.replace('<<rule>>', b.left)

        stmts_code = ''
        for i in range(len(b.right)):
            # statement first set
            if_code = ''
            for t in b.first_set[i]:
                if t[0] == '<':
                    if_code += '||lookahead.type().equals(TokenType.'\
                        + t[1:-1] + ')'
                elif t[0] == '"':
                    if_code += '||lookahead.value().equals(' + t + ')'
            if_code = if_code[2:]

            # statement body
            body_code = ''
            for t in b.right[i]:
                if t in terminal_set:
                    if t[0] == '<':
                        body_code += 'match(TokenType.' + t[1:-1] + '); '
                    elif t[0] == '"':
                        body_code += 'match(' + t + '); '
                elif t in non_terminal_set:
                    body_code += t + '();'

            stmts_code += statements_template \
                .replace('<<statement_first_set>>', if_code) \
                .replace('<<statement>>', body_code)

        stmts_code = stmts_code.replace('else ', '', 1)
        code = stmts_pattern.sub(stmts_code, code)
        all_code += code

    template = rule_pattern.sub(all_code, template)
    # add entry
    template = template.replace('<<entry>>', entry.left + '();')

    out_file = open('Parser.java', 'w')
    out_file.write(template)
    out_file.close()
