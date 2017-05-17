import pandas as pd
import matplotlib.pyplot as plt
import os
import re
from collections import OrderedDict
import csv

os.chdir('../data_final')
files = os.listdir()
# files = ['1111.csv']
lang_str = 'C|C++|C#|PYTHON|JAVA|JAVASCRIPT|PHP|HTML|SQL|CSS|R|RUBY|PERL|SCALA|SWIFT|GO|MYSQL|Objective-c|VB|jQuery|ASP.NET'
language = lang_str.split('|')
# Initail all items = 0
# all_result = {key.upper():0 for key in language}

asp_regex = 'ASP'
c_sharp_regex = 'C#\.NET|visual C\#'
vb_regex = 'VB.NET|VBA|Visual basic|Visual basic \.net'
objc_regex = 'object c'

for file in files:
    print(file)
    with open(file, 'r', encoding='utf-8') as f:
        all_result = {key.upper(): 0 for key in language}
        pairs = f.read().split('\n')
        pairs = [pair for pair in pairs[:100] if len(pair)]
        lan = [pair.split(',')[0].upper() for pair in pairs]
        counts = [pair.split(',')[1] for pair in pairs]
        result = {lan: count for lan, count in zip(lan, counts)}
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
                all_result['Objective-c'] += int(result[key])
    result_list = [(k,v) for (k,v) in all_result.items()]
    order_dict = OrderedDict(result_list)
    with open("../final_cata.csv", 'a') as fw:
        [fw.write('{},{},{}\n'.format(k, v, file.split('.csv')[0])) for k, v in order_dict.items()]
    df = pd.DataFrame(result_list)
    df.columns = ['Language', 'Counts']

    df = df.set_index(['Language'], drop=True)

    # Sort dataframe
    sort_df = df.sort_values(by="Counts", ascending=False)

    # Plot chart
    sort_df.plot(kind='bar', title='Programming Languages Popularity', fontsize=10, legend=False, colormap='Oranges_r')
    plt.show()