wget -nd -P base_commands_download -r -np -A .sh "http://helio.mssl.ucl.ac.uk/usdownloads/Helio/$HEC_BASE_DATE/" 
mv base_commands_download/* base_commands_toprocess
find base_commands_processed -type f -printf "%f\n" | ./remove_aready_processed_commands.sh
find base_commands_toprocess -type f -exec sh {} \;
\cp base_commands_toprocess/* base_commands_processed
\rm base_commands_toprocess/*
echo "It will be common to see the cp and rm command say 'No File or directory'.  This is because the commands have already been processed on some earlier date with no need to run them again and yet it tries to copy any files that were processed into the appropriate directory.  This could be fixed by redirecting to /dev/null, but for now will let the error message stay"
