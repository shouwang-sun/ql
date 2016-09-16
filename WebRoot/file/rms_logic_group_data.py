# -*- coding: utf-8 -*-
__author__ = 'SUNShouwang'

import numpy as np


def rms_logic_group(input_dict):
    """
    Return the RMS(Root Mean Square) of each channel in input_dict

    Call signature::
        output_dict = rms_logic_group(input_dict)

    Arguments::
        input_dict: {'ch1': {'time': [], 'data': []}, 'ch2': {'time': [], 'data': []}, ..., 'dt': scalar}

    Results:
        output_dict: {'ch1': {'time': [], 'result': []}, 'ch2': {'time': [], 'result': []}, ...}
    """

    root = np.sqrt
    mean = np.mean
    square = np.square

    keys = input_dict.keys()
    keys.remove('dt')
    output_dict = {key: {'time': [], 'result': []} for key in keys}
    dt = input_dict['dt']

    for key in keys:
        t0 = input_dict[key]['time'][0]

        time = input_dict[key]['time']
        if time is not np.ndarray:
            time = np.array(time)

        data = input_dict[key]['data']
        if data is not np.ndarray:
            data = np.array(data)

        while t0 < time[-1]:
            rms = root(mean(square(data[(time >= t0) & (time < t0 + dt)])))
            output_dict[key]['time'].append(t0)
            output_dict[key]['result'].append(rms)
            t0 += dt

    return output_dict


if __name__ == '__main__':
    # input_dict = {
    # 'dt': 2,
    #     'SEW1112-DX': {'time': np.arange(0, 10, 1), 'data': np.arange(0, 1, 0.1)},
    #     'SEW1112-DY': {'time': np.arange(0, 10, 1), 'data': np.arange(0, 2, 0.2)}
    # }
    input_dict = {
        'dt': 2,
        'SEW1112-DX': {'time': [i for i in range(10)], 'data': [i / 0.4 for i in range(23)]},
        'SEW1112-DY': {'time': [i for i in range(10)], 'data': [i / 0.2 for i in range(34)]}
    }
    output_dict = rms_logic_group(input_dict)
    print output_dict