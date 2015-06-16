#!/bin/bash

while read data; do
    echo "Attempting to Remove = $data expect common remove error if file does not exist"
    rm "base_commands_toprocess/$data"
done
