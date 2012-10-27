package jarvis.gribdownloadspring;

/*
 * #%L
 * Camel for GFS data via FTP
 * %%
 * Copyright (C) 2012 Adrian Jarvis
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import java.util.Date;
import ucar.grib.grib2.*;

/**
 *
 * @author Adge
 */
public class Grib2RecordFacade {

    private final Grib2Record record;
    @SuppressWarnings("deprecation")
    private final Grib2ProductDefinitionSection productDefinitionSection;
    private final Grib2IndicatorSection indicatorSection;
    private final Grib2Pds pdsVars;
    

    public Grib2RecordFacade(Grib2Record record) {
        this.record = record;
        productDefinitionSection = record.getPDS();
        indicatorSection = record.getIs();
        pdsVars = productDefinitionSection.getPdsVars();
    }
    
    public String getParameterName() {
        return ParameterTable.getParameterName(indicatorSection.getDiscipline(), 
                pdsVars.getParameterCategory(), pdsVars.getParameterNumber());
    }

    public Date getForecastDate() {
        return pdsVars.getForecastDate();
    }

    public int getParameterNumber() {
        return pdsVars.getParameterNumber();
    }

    public int getFirstLevelType() {
        return pdsVars.getLevelType1();
    }
    
    public double getFirstLevelValue() {
        return pdsVars.getLevelValue1();
    }
    
    public String getFirstLevelName() {
        double levelValue1 = pdsVars.getLevelValue1();
        String levelName = Grib2Tables.codeTable4_5(pdsVars.getLevelType1());
        return String.format("%10.4f%s", levelValue1, levelName);
    }

    public String getSecondLevelName() {
        double levelValue = pdsVars.getLevelValue2();
        String levelName = Grib2Tables.codeTable4_5(pdsVars.getLevelType2());
        return String.format("%10.4f%s", levelValue, levelName);
    }
}
