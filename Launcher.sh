#!/bin/bash

export CLASSPATH=.:dist/*
java -Xmx4096M -server -Dnet.sf.odinms.wzpath=wz server.Start
