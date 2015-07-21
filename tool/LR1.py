"""
This file is to convert a grammar file written in BNF to LR(1) parser.
It will return a LR(1) parsing table.
"""
import re

bnf = None
bnf_dic = None
entry = None
terminal_set = set()
non_terminal_set = set()
state_set = {}
table = {}


def generate(file_name):
    global bnf
    global entry
    global state_set

    BNF.postfix = 0
    first_set.dic = {}
    first_set.recursion = set()

    grammar_file = open(file_name)
    bnf = [BNF(line) for line in grammar_file.readlines()]
    grammar_file.close()

    print '\nextend......'
    extend()
    print 'BNF length: %d' % len(bnf)

    print '\nremove repeated item......'
    remove_repeated_item()
    print 'BNF length: %d' % len(bnf)

    entry = bnf[0]
    if len(entry.right) > 1:
        bnf.insert(0, BNF('_entry_::=' + entry.left))
        entry = bnf[0]

    generate_bnf_set()
    print_bnf()

    s0 = State()
    s0.items[StateItem(entry.left, entry.right[0], 0)] = set([''])
    s0.closure()

    state_set[s0] = s0
    goto()

    gen_table()


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


def generate_bnf_set():
    global bnf
    global bnf_dic
    global terminal_set
    global non_terminal_set

    bnf_dic = {b.left: b.right for b in bnf}

    for b in bnf:
        non_terminal_set.add(b.left)
        for s in b.right:
            for i in s:
                if i[0] == '"' or i[0] == '<':
                    terminal_set.add(i)


class Hashable:
    def __hash__(self):
        return hash(str(self))

    def __eq__(self, b):
        return str(self) == str(b)

    def __cmp__(self, b):
        if str(self) < str(b):
            return -1
        elif str(self) > str(b):
            return 1
        else:
            return 0


class State(Hashable):
    def __init__(self):
        self.items = {}
        self.edges = {}

    def closure(self):
        itered_set = set()
        while True:
            tmp = self.items.copy()

            for item, search_set in tmp.iteritems():
                if item in itered_set:
                    continue

                itered_set.add(item)
                search_set = first(item.stmt[item.dot + 1:], search_set)
                self.append(item.closure(), search_set)

            if len(tmp) == len(self.items):
                break

    def append(self, items, search_set):
        for item in items:
            if item in self.items:
                self.items[item] = self.items[item].union(search_set)
            else:
                self.items[item] = search_set

    def goto(self):
        for item, search_set in self.items.iteritems():
            if len(item.stmt) == item.dot:
                continue

            word = item.stmt[item.dot]
            state = None
            if word in self.edges:
                state = self.edges[word]
            else:
                state = State()
                self.edges[word] = state

            state.items[StateItem(item.left, item.stmt, item.dot + 1)] =\
                search_set

        for state in self.edges.values():
            state.closure()

    def __str__(self):
        re = ''
        for key in self.items:
            re += str(key) + '\n'
        return re


def first(stmt, search_set):
    if len(stmt) == 0:
        return search_set
    elif stmt[0] in terminal_set:
        return set([stmt[0]])
    elif stmt[0] in non_terminal_set:
        re = first_set(stmt[0])

        if '' in re:
            re = re.copy()
            re.remove('')
            re = re.union(first(stmt[1:], search_set))

        return re


def first_set(nonterminal):
    # prevent left recursion
    first_set.recursion.add(nonterminal)

    if nonterminal in first_set.dic:
        first_set.recursion.remove(nonterminal)
        return first_set.dic[nonterminal]
    else:
        re = first_set.dic[nonterminal] = set()
        for s in bnf_dic[nonterminal]:
            if len(s) == 0:
                re.add('')
            elif s[0] in terminal_set:
                re.add(s[0])
            elif s[0] in non_terminal_set and s[0] not in first_set.recursion:
                re = re.union(first_set(s[0]))
                first_set.dic[nonterminal] = re

        first_set.recursion.remove(nonterminal)
        return first_set.dic[nonterminal]


class StateItem(Hashable):
    dic = {}

    def __init__(self, left, stmt, dot):
        self.left = left
        self.stmt = stmt
        self.dot = dot

        re = self.left + ' ::='

        stmt = self.stmt[:]
        stmt.insert(self.dot, '@')

        for i in stmt:
            re += ' ' + i

        self.str = re

    def closure(self):
        re = []

        if self.dot == len(self.stmt):
            return re

        word = self.stmt[self.dot]
        if word in non_terminal_set:
            if word in StateItem.dic:
                return StateItem.dic[word]

            for s in bnf_dic[word]:
                re.append(StateItem(word, s, 0))
            StateItem.dic[word] = re

        return re

    def __str__(self):
        return self.str


def goto():
    while True:
        tmp = state_set.copy()

        for state in tmp:
            if len(state.edges) > 0:
                continue
            else:
                state.goto()
                for s in state.edges.values():
                    if s not in state_set:
                        state_set[s] = s
                    else:
                        for i in s.items:
                            state_set[s].items[i] =\
                                state_set[s].items[i].union(s.items[i])

        if len(tmp) == len(state_set):
            break


def gen_table():
    global table

    states = state_set.values()
    state_dic = {states[i]: i for i in range(len(states))}

    for state, i in state_dic.iteritems():
        table[i] = {}
        for e, goto in state.edges.iteritems():
            table[i][e] = state_dic[goto]
        for item in state.items:
            if len(item.stmt) == item.dot:
                for t in state.items[item]:
                    if t in table[i]:
                        print 'ERROR: conflict!!!'
                    table[i][t] = item
