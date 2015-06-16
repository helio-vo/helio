sed -i 's/STEREO_A/A/g' manLoaded/stereoa_impactplastic_sir.txt
sed -i 's/STEREO_B/B/g' manLoaded/stereob_impactplastic_sir.txt
sed -i 's/DG/ /g' manLoaded/stereoa_impactplastic_sir.txt
sed -i 's/DG/ /g' manLoaded/stereob_impactplastic_sir.txt
sed -i 's/STEREO_A/A/g' manLoaded/stereo_impactplastic_icme.txt
sed -i 's/STEREO_B/B/g' manLoaded/stereo_impactplastic_icme.txt
sed -i 's/A,B/AB/g' manLoaded/stereo_impactplastic_icme.txt
psql -d hec -f hec_insert_man.sql
