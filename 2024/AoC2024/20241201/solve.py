import pandas as pd

input = 'input.txt'
# input = 'test-input.txt'

# part 1
data = open(input, 'r').read().split('\n')

split = [entry.split() for entry in data]

def part1():
    # let's refresh list comprehensions
    lists = [[], []]
    [lists[0].append(bla[0]) for bla in split]
    [lists[1].append(bla[1]) for bla in split]
    sorted_lists = [sorted(bla) for bla in lists]

    delta = [abs(int(sorted_lists[0][bla])-int(sorted_lists[1][bla])) for bla in range(len(sorted_lists[0]))]

    result = sum(delta)
    print('part 1: {}'.format(result))

# part 2
#  now I try with pandas
def get_frequency(left, nofOccurencesRight):
    if left in nofOccurencesRight.index:
        nof = nofOccurencesRight.loc[left]
        return nof
    else:
        return 0


def part2():
    df = pd.DataFrame(split, columns=['left','right'])
    df = df.astype({'left':'int', 'right':'int'})
    grouped = df.groupby('right').nunique()


    df['frequency'] = df.apply(lambda row:get_frequency(row['left'], grouped), axis=1)

    df['mult'] = df.apply(lambda row:row['left'] * row['frequency'], axis=1)

    result = df['mult'].sum()
    print('part 2: {}'.format(result['left']))


part1()
part2()
