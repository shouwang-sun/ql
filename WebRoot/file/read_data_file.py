# -*- coding: utf-8 -*-
__author__ = 'SUNShouwang'


from data_file import DataFileFactory


def read_data_file(directory, file_name):
    data_file = DataFileFactory.create_file(directory, file_name)
    time_data = data_file.read()
    return time_data