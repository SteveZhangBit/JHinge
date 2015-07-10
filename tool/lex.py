"""
This is a lexer generator. The lexer uses regular expression to parse.
"""


def generate(file_name):
    lex_file = open(file_name)

    tokens = [Token(line) for line in lex_file.readlines()]
    for t in tokens:
        print t

    lex_file.close()

    fill_token_temp(tokens)
    fill_lexer_temp(tokens)


class Token():
    def __init__(self, line):
        index = line.find('::=')
        self.name = line[:index].strip()
        self.value = line[index + 3:].strip()

        if self.name[0] == '?':     # skip token
            self.name = self.name[1:]
            self.skip = True
        else:
            self.skip = False

    def __str__(self):
        return '%-20s %s' % (self.name + ('<skip>' if self.skip else ''),
                             self.value)


def fill_token_temp(tokens):
    temp_file = open('token-template.java')
    template = ''
    for line in temp_file.readlines():
        template += line

    code = ''
    for t in tokens:
        code += '\t\t%s("%s"),\n' % (t.name, t.value)

    template = template.replace('<<token>>', code[2:-1])

    out_file = open('Token.java', 'w')
    out_file.write(template)
    out_file.close()


def fill_lexer_temp(tokens):
    temp_file = open('lexer-template.java')
    template = ''
    for line in temp_file.readlines():
        template += line

    code = ''
    for t in tokens:
        if t.skip:
            code += 'type.equals(TokenType.%s) || ' % t.name
    if code == '':
        code = 'false'
    else:
        code = code[:-4]

    template = template.replace('<<skip_token>>', code)

    out_file = open('Lexer.java', 'w')
    out_file.write(template)
    out_file.close()
