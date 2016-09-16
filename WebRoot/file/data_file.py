# -*- coding: utf-8 -*-
__author__ = 'SUNShouwang'

"""
Base class: DataFile
    subclass: DataFileYD
    subclass: DataFileND
    subclass: DataFileYS

Factory class: DataFileFactory
"""


from time import strptime, mktime, localtime
from types import StringType, TupleType, UnicodeType
from os import path
from re import search
import numpy as np


pattern = '(?P<bridge_station>\d*)' \
          '(?P<sensor_channel>[A-Z]+\d+-?[A-Z]+\d?)#' \
          '(?P<file_format1>[TF]#[DH]#|)' \
          '(?P<start_time>\d{14,20})#' \
          '(?P<file_format2>[YN]#[DS]#|)' \
          '(?P<dt_num>\d+)' \
          '(?P<dt_unit>[A-Z]+)#?'

units_to_ms = {'MS': 1, 'S': 1000, 'MIN': 60000, 'H': 3600000}

data_type = {
    'T#H#': np.dtype([('time', '>u8'), ('data', '>f4')]),
    'T#D#': np.dtype([('time', '>u8'), ('data', '>f4')]),
    'Y#D#': np.dtype([('time', '>u8'), ('data', '>f4')]),
    'F#D#': np.dtype('>f4'),
    'F#H#': np.dtype('>f4'),
    'N#D#': np.dtype('>f4'),
    'Y#S#': np.dtype([('time', np.float), ('data', np.bool)])
}


def convert_to_ms(num, unit):
    try:
        unit = unit.upper()
        return num * units_to_ms[unit]
    except KeyError:
        print 'Unexpected time unit'
        return None


def date_to_num(date_time):
    if type(date_time) in [UnicodeType, StringType] and date_time.isdigit:
        date_time.ljust(20, '0')
        return 1000 * mktime(strptime(date_time, '%Y%m%d%H%M%S%f'))
    elif type(date_time) is TupleType or type(date_time) is type(localtime()):
        return 1000 * mktime(date_time)
    else:
        print('incorrect time input')


class DataFile:
    """base class of all three types of data files"""

    def __init__(self, directory, file_name):
        self.directory = directory
        self.file_name = file_name

    @property
    def bridge_station(self):
        return search(pattern, self.file_name).groupdict().get('bridge_station')

    @property
    def sensor_channel(self):
        return search(pattern, self.file_name).groupdict().get('sensor_channel')

    @property
    def start_time(self):
        date_time = search(pattern, self.file_name).groupdict().get('start_time')
        return date_to_num(date_time)

    @property
    def data_type(self):
        file_format1 = search(pattern, self.file_name).groupdict().get('file_format1')
        file_format2 = search(pattern, self.file_name).groupdict().get('file_format2')
        file_format = file_format1 if file_format1.__len__() != 0 else file_format2
        return data_type[file_format]

    @property
    def dt_ms(self):
        dt_num = search(pattern, self.file_name).groupdict().get('dt_num')
        dt_unit = search(pattern, self.file_name).groupdict().get('dt_unit')
        return convert_to_ms(int(dt_num), dt_unit)

    def read(self):
        pass


class DataFileYD(DataFile):
    """data files that are binary and have timestamps"""

    def read(self):
        source = path.join(self.directory, self.file_name)
        time_data = np.fromfile(source, dtype=self.data_type, count=-1)
        time_data['time'] = np.uint64(self.start_time + np.arange(time_data.size) * self.dt_ms)
        return time_data


class DataFileND(DataFile):
    """data files that are binary and have no timestamp"""

    def read(self):
        source = path.join(self.directory, self.file_name)
        file_data = np.fromfile(source, dtype=self.data_type, count=-1)
        file_time = np.uint64(self.start_time + np.arange(file_data.size) * self.dt_ms)
        time_data = np.zeros(file_data.size, dtype=np.dtype([('time', '>u8'), ('data', '>f4')]))
        time_data['data'] = file_data
        time_data['time'] = file_time
        return time_data


class DataFileYS(DataFile):
    """data files that are strings and have timestamps"""

    def read(self):
        source = path.join(self.directory, self.file_name)
        gbkcmp = lambda s: u'正常' == s.decode('gbk')
        time_data = np.loadtxt(source, dtype=self.data_type, delimiter='/', converters={0: date_to_num, 1: gbkcmp})
        return time_data


class DataFileFactory:
    """
    Create object of the three subclasses of DataFile class according to given file names
    """

    @staticmethod
    def create_file(directory, file_name):
        if any([file_name.__contains__(x) for x in ['#T#D', '#T#H', '#Y#D']]):
            return DataFileYD(directory, file_name)
        elif any([file_name.__contains__(x) for x in ['#F#D', '#F#H', '#N#D']]):
            return DataFileND(directory, file_name)
        elif file_name.__contains__('#Y#S'):
            return DataFileYS(directory, file_name)
        else:
            print


if __name__ == "__main__":
    import doctest
    doctest.testfile(verbose=True)