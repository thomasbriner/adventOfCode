from types import new_class

import numpy
import pandas as pd
from pprint import pprint
import re

input = 'input.txt'
# input = 'test-input.txt'

# part 1
data = open(input, 'r').read()

result = 0


def part1():
    print('part 1: {}'.format(result))


# part 2
def part2():
    print('part 2: {}'.format(result))


# part1()
# part2()


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

print('part 1: {}'.format(result))

# [0-9]{1,3}
