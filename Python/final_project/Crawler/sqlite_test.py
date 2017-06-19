import sqlite3


content_dict = {'網站': 'icars',
                '連結': 'url',
                '代號': '0174150093', '廠牌': 'AUDI',
                '型號': 'A4 AVANT', '車型': '轎車',
                '車門': '五門以上', '排擋方式': '手自排',
                '排氣量': '1800', '所在地': '台中市',
                '年份': '2005年', '色系': '其他',
                '行駛里程': '0公里', '如需專業汽車鑑定': '',
                '燃料稅': '4800元', '牌照稅': '7120元',
                '價格': '38.8', '時間': '1492185600',
                '標題': 'AUDI  A4 AVANT（2005年)',
                '配備': '皮椅|天窗|防盜|倒車雷達|倒車顯影|衛星導航|行車電腦|ABS(防鎖死煞車系統)|CD|DVD|電動座椅|安全氣囊'}

conn = sqlite3.connect('/home/ubuntu/python/III/Python/final_project/Crawler/icars.db')
cursor = conn.cursor()

cursor.execute('''CREATE TABLE icars
             (source text, url text, title text, brand text, model text, doors text, color text,
             cc text, transmission text, equip text, mileage text, years text, location text, posttime text, price text,
             certificate text)''')
conn.commit()


# source = content_dict['網站']
# url = content_dict['連結']
# title = content_dict['標題']
# brand = content_dict['廠牌']
# model = content_dict['型號']
# doors = content_dict['車門']
# color = content_dict['色系']
# cc = content_dict['排氣量']
# transmission = content_dict['排擋方式']
# equip = content_dict['配備']
# mileage = content_dict['網站']
# years = content_dict['行駛里程']
# location = content_dict['所在地']
# posttime = content_dict['時間']
# price = content_dict['價格']
# certificate = ''
#
#
# cursor.execute('INSERT INTO icars VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)',
#                (source, url , title, brand, model, doors, color, cc, transmission, equip,
#                 mileage, years, location, posttime, price, certificate))
#
# conn.commit()
#
#
# title = content_dict['標題']
# color = content_dict['色系']
# cc = content_dict['排氣量']
# equip = content_dict['配備']
#
#
# num = list(cursor.execute('SELECT * FROM icars WHERE title = ? AND cc = ? AND color = ?  AND equip = ? AND location = ?',
#                (title, cc, color, equip, location)))
# print(len(num))
