#!/usr/bin/env python3

import logging
import os
import sys


class CustomLogger(object):

    def __init__(self, output_dir):
        self._LOG_DIR = output_dir

    def get_logger(self):
        if not os.path.exists(self._LOG_DIR):
            try:
                os.makedirs(self._LOG_DIR)
            except Exception as e:
                sys.exit('Unable make directory : ' + self._LOG_DIR + "\nCause : " + str(e))

        logger = logging.getLogger()
        logger.setLevel(logging.DEBUG)

        # create console handler and set level to info
        handler = logging.StreamHandler()
        handler.setLevel(logging.INFO)
        formatter = logging.Formatter("%(message)s")
        handler.setFormatter(formatter)
        logger.addHandler(handler)

        # create error file handler and set level to error
        handler = logging.FileHandler(os.path.join(self._LOG_DIR, "error.log"), "w", encoding=None, delay="true")
        handler.setLevel(logging.ERROR)
        formatter = logging.Formatter("%(asctime)s [%(module)s] (%(funcName)s:%(lineno)d)  %(message)s")
        handler.setFormatter(formatter)
        logger.addHandler(handler)

        # create debug file handler and set level to debug
        handler = logging.FileHandler(os.path.join(self._LOG_DIR, "all.log"), "w")
        handler.setLevel(logging.DEBUG)
        formatter = logging.Formatter("%(asctime)s [%(levelname)s]\t(%(module)s:%(lineno)d)  %(message)s")
        handler.setFormatter(formatter)
        logger.addHandler(handler)

        logger.info("Logging output in : "+self._LOG_DIR+"/all.log")
        logger.info("Error output in : "+self._LOG_DIR+"/error.log")
        return logger
