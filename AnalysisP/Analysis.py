import matplotlib.pyplot as plt

def read_to_list(file):
    line = []
    with open(file, 'r') as f:
        for l in f.readlines():
            line = l.strip().split(',')

    del line[-1]

    for ind in range(len(line)):
        line[ind] = float(line[ind])

    return line


means = []
for i in range(101, 401):
    means.append(i / 100)

variances = [1, 10, 20, 50]

mrt = {}

for i in variances:
    name = "variance_" + str(i) + "_lambda_squared"
    mrt[i] = read_to_list(name + ".csv")
    r = []

fig = plt.figure()
ax = fig.add_subplot(1, 1, 1)
ax.set_xlabel('Expected Value')
ax.set_ylabel('Mean Response Time')
ax.set_title('Mean Response Time vs Expected Value with Multiple Variances')

colors = ['orange', 'blue', 'red', 'green']

for c, v in enumerate(variances):
    ax.plot(means, mrt[v], color=colors[c], alpha=0.3, label=("Variance: " + str(v) + " * Expected Value Squared"),
            markersize=5)

ax.legend(loc='upper center', prop={'size': 8})
plt.tight_layout()
plt.savefig("Analysis.png", dpi=300)
