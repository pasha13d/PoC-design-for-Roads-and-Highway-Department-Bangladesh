#!/usr/bin/env python3

import sys
import json
import pandas as pd
import pygal
from jinja2 import Environment, FileSystemLoader, select_autoescape

env = Environment(
    loader=FileSystemLoader('templates')
)
_outputfile = './log/regression-report.html'


def services_pie_chart(d):
    # takes a dictionary as input
    '''
    d = {'passed': [{'value': 70, 'label': 'regression coverage', 'color': 'green'}], 'failed': [{'value': 30, 'label': 'regression coverage', 'color': 'red'}]}
    '''
    pie_chart = pygal.Pie(show_legend=False, half_pie=True, margin=0, explicit_size=True, height=90, width=200,
                          print_values=True, disable_xml_declaration=True)
    for key in d:
        pie_chart.add(key, d[key])
    return pie_chart.render()
    # pie_chart.render_to_file('services.svg')


def outer_join(l, r):
    # l and r are two list
    pl = pd.DataFrame({'key': l, 'val': l})
    pr = pd.DataFrame({'key': r, 'val': r})
    result = pd.merge(pl, pr, how='outer', on=['key'])
    return [(idx, row[1], row[2]) for idx, row in result.iterrows()]


def to_link(value):
    # return '<a href="#' + value + '">' + value + '</a>'
    return list(map(lambda p: '<a href="#' + p + '">' + p + '</a>', value.split(',')))


def report(coverage):
    env.filters['to_link'] = to_link
    env.globals.update(outer_join=outer_join)
    env.globals.update(services_pie_chart=services_pie_chart)
    template = env.get_template('regression-report-template.html')
    return template.render(d=coverage)


def output(result):
    with open(_outputfile, 'w+') as f:
        print(result, file=f)


def coverage_data(file):
    with open(file, 'r') as f:
        data = json.loads(f.read())

    return data


def generate_report(data):
    # data = coverage_data(data)
    html = report(data)
    output(html)

