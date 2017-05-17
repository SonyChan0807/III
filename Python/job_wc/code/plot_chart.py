import pandas as pd
import matplotlib.pyplot as plt
import os
import re
from collections import OrderedDict
import csv

os.chdir(os.path.join(os.getcwd(), '../data_final'))
files = os.listdir()

lang_str = 'C|C++|C#|PYTHON|JAVA|JAVASCRIPT|PHP|HTML|SQL|CSS|R|RUBY|PERL|SCALA|SWIFT|GO|MYSQL|Objective-c|VB|jQuery|ASP.NET'
language = lang_str.split('|')
# Initail all items = 0
all_result = {key.upper():0 for key in language}

asp_regex = '.net|ASP'
c_sharp_regex = 'C#.NET'
vb_regex = 'VB.NET|VBA|Visual basic|visual'
objc_regex = 'object c'

for file in files:
#     print(file)
    with open(file, 'r', encoding='utf-8') as f:
        pairs = f.read().split('\n')
        pairs = [pair for pair in pairs if len(pair)]
        lan = [pair.split(',')[0].upper() for pair in pairs]
        counts = [pair.split(',')[1] for pair in pairs]
        result = dict(zip(lan, counts))  # {k:v for (k,v) in zip(lan, couonts)}
        for key in result.keys():
            if key in all_result.keys():
                all_result[key] += int(result[key])
            if re.match(asp_regex, key, re.IGNORECASE):
                all_result['ASP.NET'] += int(result[key])
            if re.match(c_sharp_regex, key, re.IGNORECASE):
                all_result['C#'] += int(result[key])
            if re.match(vb_regex, key, re.IGNORECASE):
                all_result['VB'] += int(result[key])
            if re.match(objc_regex, key, re.IGNORECASE):
                all_result['VB'] += int(result[key])
result_list = [(k,v) for (k,v) in all_result.items()]

order_dict = OrderedDict(result_list)


df = pd.DataFrame(result_list)
df.columns = ['Language', 'Counts']

df = df.set_index(['Language'], drop=True)

# Sort dataframe
sort_df = df.sort_values(by="Counts", ascending=False)

# Plot chart
sort_df.plot(kind='bar', title='Programming Languages Popularity', fontsize=10, legend=False, colormap='Oranges_r')
plt.show()

print(sort_df)

Languages = [key for key in all_result.keys()]
Counts = [value for value in all_result.values()]

df2 = pd.DataFrame(Counts, index=Languages, columns=['Counts'])
# df2 = pd.Series(Counts, index=pd.Series(Languages))    #Alternative way to create series

sort_df2 = df2.sort_values(by="Counts", ascending=True)
sort_df2.to_csv('../final.csv', encoding='utf-8')

sort_df2.plot(kind='barh', title='Programming Languages Popularity', fontsize=10, legend=False)
# print(sort_df2)

plt.show()

