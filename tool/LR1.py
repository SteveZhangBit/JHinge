"""
This file is to convert a grammar file written in BNF to LR(1) parser.
It will return a LR(1) parsing table.
"""


terminals = set()
non_terminals = set()


class BNF:
    """docstring for BNF"""
    def __init__(self, statement):
        statement = statement.split('::=')

        self.left = statement[0].strip()
        non_terminals.add(self.left)

        right = statement[1].strip()
        self.right = []
        for sentence in right.split(' | '):
            sentence = sentence.strip()
            self.right.append(sentence)
            # Add each word to terminals or non_terminals
            for word in sentence.split(' '):
                if word[0] == '<' or word[0] == '"':
                    terminals.add(word)
                else:
                    non_terminals.add(word)

    def __str__(self):
        re = self.left + " ::= "
        for s in self.right:
            re += s + ' | '
        return re


class State():
    """docstring for State"""
    # Using a dictionary to represent the items. The key is the string value
    # for a sentence, and the value is the lookahead value set.
    def __init__(self):
        self.items = {}
        self.edges = {}

    def closure(self):
        while True:
            tmp = self.items.copy()

            for key, lookahead in self.items.iteritems():
                word = key.right[key.dot]
                if word in non_terminals:
                    lookahead_new = self.first(key.right[key.dot:], lookahead)

                    for s in bnf_dic[word]:
                        key_new = StateItem(word, s, 0)
                        if key_new in tmp:
                            tmp[key_new] = tmp[key_new].union(lookahead_new)
                        else:
                            tmp[key_new] = lookahead_new

            if len(tmp) == len(self.items):
                break
            else:
                self.items = tmp

    def first(self, statement, lookahead):
        """FIRST(ab)"""
        word = statement[1]
        if word in terminals:
            return set([word])
        elif word in non_terminals:
            return self.first_set(word)
        else:
            return lookahead

    def first_set(self, word):
        """FIRST Set in LL(1)"""
        re = set()
        for s in bnf_dic[word]:
            i = s.split(' ')[0]
            if i in terminals:
                re.add(i)
            else:
                re = re.union(self.first_set(i))
        return re

    def goto(self):
        for key, lookahead in self.items.iteritems():
            word = key.right[key.dot]

            if word == '#':
                continue

            key_new = key.copy()
            key_new.dot += 1
            lookahead_new = lookahead.copy()

            if word not in self.edges:
                state = State()
                state.items[key_new] = lookahead_new
                self.edges[word] = state
            else:
                self.edges[word].items[key_new] = lookahead_new

        for edge in self.edges:
            self.edges[edge].closure()

    def __str__(self):
        s = ''
        for key, val in self.items.iteritems():
            s += str(key) + ', ' + str(val) + '\n'
        return s

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


class StateItem():
    """Item in state"""
    def copy(self):
        item = StateItem()
        item.left = self.left
        item.right = self.right
        item.dot = self.dot
        return item

    def __init__(self, left='', right='', dot=None):
        self.left = left
        self.dot = dot
        right = right + ' #'
        self.right = right.split(' ')

    def __str__(self):
        tmp = self.right[:]
        tmp.insert(self.dot, '@')

        s = ''
        for i in tmp:
            if i == '#':
                s += i
            else:
                s += i + ' '

        return self.left + ' ::= ' + s

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


# Create BNF object from file
grammar_file = open('grammar-LR.txt')
bnf = [BNF(line) for line in grammar_file.readlines()]

# Determine the entry of the grammar, and make it to extensional grammar
entry = bnf[-1]
if len(entry.right) != 1:
    bnf.append(BNF('entry::=' + entry.left))
    entry = bnf[-1]
bnf_dic = {i.left: [j for j in i.right] for i in bnf}

# Initialize for creating DFA for LR(1)
s0 = State()
s0.items[StateItem(entry.left, entry.right[0], 0)] = set(['#'])
s0.closure()

# Generate LALR(1)
state_set = set([s0])
while True:
    tmp = state_set.copy()

    for state in state_set:
        # Generate GOTO for each state
        state.goto()
        for edge, goto_state in state.edges.iteritems():
            flag = True
            # For each state generated by goto, combine it to the state which
            # has the same rules.
            for state in state_set:
                if state.items.keys() == goto_state.items.keys():
                    flag = False
                    for key, val in state.items.iteritems():
                        state.items[key] = val.union(goto_state.items[key])
                    break
            if flag:
                tmp.add(goto_state)

    if len(tmp) == len(state_set):
        break
    else:
        state_set = tmp

print 'Total length %d' % len(state_set)
out = open('out.txt', 'w')
for state in state_set:
    out.write(str(state))
