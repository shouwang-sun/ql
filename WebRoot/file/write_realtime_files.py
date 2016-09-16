# -*- coding: utf-8 -*-
__author__ = 'SUNShouwang'


from time import localtime, strftime
import os
from data_file import DataFileFactory


if __name__ == '__main__':

    source_directory = u'e:\\MelakBridge\\HistoryData'

    destination_directory = u'e:\\MelakBridge\\RealtimeData'

    localtime_ms = lambda ms: localtime(ms / 1000)

    for root, dirs, files in os.walk(source_directory, topdown=True):
        for file_name in files:
            f = DataFileFactory.create_file(root, file_name)
            sensor_channel = f.sensor_channel
            time_data = f.read()
            t0 = time_data['time'][0]
            while t0 <= time_data['time'][-1]:

                date_str = strftime('%Y%m%d', localtime_ms(t0))
                if not os.path.exists(os.path.join(destination_directory, date_str)):
                    os.mkdir(os.path.join(destination_directory, date_str))

                realtime_file_name = f.file_name.replace(
                    strftime('%Y%m%d%H%M%S', localtime_ms(f.start_time)),
                    strftime('%Y%m%d%H%M%S', localtime_ms(t0))
                )
                logical_index = (time_data['time'] >= t0) & (time_data['time'] < t0 + 120000)
                time_data[logical_index].tofile(
                    os.path.join(destination_directory, date_str, sensor_channel, realtime_file_name)
                )
                t0 += 120000