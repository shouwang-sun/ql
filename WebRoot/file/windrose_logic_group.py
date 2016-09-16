# -*- coding: utf-8 -*-
__author__ = 'SUNShouwang'

import os
import json
import sys

import numpy as np
from WindRose import WindroseAxes
import pylab as plt


def windrose_logic_group(input_dict):
    """
    Return the 2-dimensional histogram of wind direction and speed
    and plot wind rose figure and save as a jpeg image in specified directory

    Call signature::
    output_dict = windrose_logic_group(input_dict)

    Arguments::
        input_dict: {
            'wind_direction': {'time': [], 'data': []},
            'wind_speed': {'time': [], 'data': []},
            'image_dir': string
            }

    Results:
        output_dict: {'2D_histogram': {'time': [timestamp], 'result': [json string]}}



    """
    if 'image_dir' not in input_dict.keys() or input_dict['image_dir'] is None:
        input_dict['image_dir'] = os.path.join(os.getcwd().replace('bin','webapps/qljk_pic'), 'windrose.jpeg')

    if input_dict['wind_direction']['data'] is not np.ndarray:
        input_dict['wind_direction']['data'] = np.array(input_dict['wind_direction']['data'])

    if input_dict['wind_speed']['data'] is not np.ndarray:
        input_dict['wind_speed']['data'] = np.array(input_dict['wind_speed']['data'])

    axes = WindroseAxes.from_ax()
    axes.bar(input_dict['wind_direction']['data'], input_dict['wind_speed']['data'],
             normed=False, opening=0.8, edgecolor='white')
    axes.set_legend()
    plt.savefig(input_dict['image_dir'], dpi=300)

    info = axes._info
    info['table'] = info['table'].tolist()

    output_dict = {
        '2d_histogram': {
            'time': [input_dict['wind_speed']['time'][0]],
            'result': [json.dumps(info)]
        }
    }
    return output_dict


if __name__ == '__main__':
    if sys.argv.__len__() < 2:  # No argument was provided during process building
        input_dict = {
            'wind_speed': {
                'time': np.linspace(10, 20, 500, endpoint=False).tolist(),
                'data': (6 * np.random.random(500)).tolist()
            },
            'wind_direction': {
                'time': np.linspace(10, 20, 500, endpoint=False).tolist(),
                'data': (360 * np.random.random(500)).tolist()
            }
        }
        sys.argv.append(json.dumps(input_dict))

    #input_dict = eval(sys.argv[1])
    input_dict = json.loads(sys.argv[1])

    output_dict = windrose_logic_group(input_dict)

    print output_dict
    
	