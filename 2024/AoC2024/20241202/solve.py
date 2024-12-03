from types import new_class

import numpy
import pandas as pd
from pprint import pprint

input = 'input.txt'
# input = 'test-input.txt'

# part 1
data = open(input, 'r').read().split('\n')

split = [entry.split() for entry in data]


def handle_top_level_failure(row, failing_index):
    new_row = row.copy()
    del new_row[failing_index]
    return is_correct_part2(new_row, True)


def is_correct_part2(row, is_dampened=False):
    sgn_diff = 0
    dampener_result = []
    for n in range(len(row) - 1):
        diff = int(row[n + 1]) - int(row[n])
        if diff == 0:
            if not is_dampened:
                dampener_result.append(handle_top_level_failure(row, n + 1))
            else:
                return False
        elif abs(diff) > 3:
            if not is_dampened:
                dampener_result.append(handle_top_level_failure(row, n + 1))
            else:
                return False
        elif sgn_diff != 0:
            if numpy.sign(diff) != sgn_diff:
                if not is_dampened:
                    dampener_result.append(handle_top_level_failure(row, n + 1))
                else:
                    return False
        else:
            if n == 0:
                sgn_diff = numpy.sign(diff)

    if (len(dampener_result) == 0):
        return True
    else:
        if handle_top_level_failure(row, 0):
            return True
        else:
            return any(dampener_result)


def is_correct_part2_brute_force(row):
    brute_result = []
    brute_result.append(is_correct_part1(row))
    for n in range(len(row)):
        new_row = row.copy()
        del new_row[n]
        brute_result.append(is_correct_part1(new_row))
    return any(brute_result)



def is_correct_part1(row):
    sgn_diff = 0
    for n in range(len(row) - 1):
        diff = int(row[n + 1]) - int(row[n])
        if diff == 0:
            return False
        if abs(diff) > 3:
            return False
        if sgn_diff != 0:
            if numpy.sign(diff) != sgn_diff:
                return False
        else:
            sgn_diff = numpy.sign(diff)
    return True


def part1():
    correct_rows = [bla for bla in split if is_correct_part1(bla)]
    print(correct_rows)
    result = len(correct_rows)
    print('part 1: {}'.format(result))


# part 2
def part2():
    correct_rows = [bla for bla in split if is_correct_part2_brute_force(bla)]
    pprint(correct_rows)
    result = len(correct_rows)
    print('part 2: {}'.format(result))


# part1()
part2()
