#!/usr/bin/env bash


SCRIPT_PATH=/media/himel/New\ Volume/EclipseProjects/tsd-to-ddl/postgres/01-table


USER=postgres
PASSWORD=321
HOST=localhost
PORT=5432
DATABASE=grp
OWNER=grp

SCHEMA_TO_CHECK=( 02-sec 03-hrm) #01-cmn  02-sec  03-hrm  04-ast  05-prc
