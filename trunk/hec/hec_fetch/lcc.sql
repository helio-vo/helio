DELETE FROM lasco_cme_cat;
COPY lasco_cme_cat (time_start,time_end,pa_central,pa_width,linear_speed,speed2_init,speed2_final,speed2_20r,acceleration,pa_measure) FROM '/var/www//hec/temp/LCC.postgres.converted';
