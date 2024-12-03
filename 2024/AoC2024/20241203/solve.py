from types import new_class
from xmlrpc.client import MAXINT

import numpy
import pandas as pd
from pprint import pprint
import re

input = 'input.txt'
# input = 'test-input.txt'
# input = 'test-input2.txt'

MULT = 'MULT'
DO = 'DO'
DONT = 'DONT'

# part 1
data = open(input, 'r').read()

result = 0


def part1():
    # target_string = "The price of PINEAPPLE ice cream is 20"
    #
    # # two groups enclosed in separate ( and ) bracket
    # result = re.search(r"(\b[A-Z]+\b).+(\b\d+)", target_string)
    #
    multiplications = re.findall(r"(mul\([0-9]{1,3},[0-9]{1,3}\))", data)

    # Extract matching values of all groups
    # Output ('PINEAPPLE', '20')

    result = 0
    for multi in multiplications:
        numbers = re.findall(r"[0-9]{1,3}", multi)
        assert len(numbers) == 2
        result += int(numbers[0]) * int(numbers[1])

    # [0-9]{1,3}
    print('part 1: {}'.format(result))


# part 2
def part2():
    multiplications = []
    for match in re.finditer(r"(mul\([0-9]{1,3},[0-9]{1,3}\))", data):
        multiplications.append({'op': MULT, 'start': match.start(), 'data': match.group()})

    dos = []
    for match in re.finditer(r"(do\(\))", data):
        dos.append({'op': DO, 'start': match.start(), 'data': match.group()})

    donts = []
    for match in re.finditer(r"(don\'t\(\))", data):
        donts.append({'op': DONT, 'start': match.start(), 'data': match.group()})

    sequence = []
    sequence.append({'op': DO, 'start': -1, 'data': ''})

    while len(multiplications) > 0 or len(dos) > 0 or len(donts) > 0:
        mul_start = multiplications[0]['start'] if len(multiplications) > 0 else MAXINT
        do_start = dos[0]['start'] if len(dos) > 0 else MAXINT
        donts_start = donts[0]['start'] if len(donts) > 0 else MAXINT
        if (mul_start < do_start and mul_start < donts_start):
            sequence.append(multiplications[0])
            del multiplications[0]
        elif (do_start < mul_start and do_start < donts_start):
            sequence.append(dos[0])
            del dos[0]
        elif (donts_start < mul_start and donts_start < do_start):
            sequence.append(donts[0])
            del donts[0]

    pprint(sequence)
    enabled = True
    result = 0
    for entry in sequence:
        if entry['op'] == DO:
            enabled = True
        elif entry['op'] == DONT:
            enabled = False
        elif entry['op'] == MULT:
            if enabled:
                numbers = re.findall(r"[0-9]{1,3}", entry['data'])
                assert len(numbers) == 2
                result += int(numbers[0]) * int(numbers[1])

    print('part 2: {}'.format(result))

part1()
part2()
