package com.fourcpplus.survey.utils;

import com.analogics.printerApi.PrinterApi;
import com.analogics.printerApi.Printer_2inch_Impact;
import com.fourcpplus.survey.model.db.ReprintData;

public class PrintHelper {

    private static final String SEPARATOR = "------------------------";

    private PrintHelper() {

    }

    public static void printTokenIssuanceSlip(ReprintData data, String gmlen, String cname, String uname) {

        try {
            PrinterApi printer = new PrinterApi();

            printer.printData(cname + "\n");
            printer.printData("     Survey Slip" + "\n");
            printer.printData(SEPARATOR);
            // printer.printData("                       " + "\n");
            //printer.printData(String.format("Clerk:%s ", data.getClerkCode()) + "\n");

            printer.printData(String.format("Season:%s ", data.getSeason()) + "\n");
            printer.printData(String.format("Date:%s ", data.getPdate()) + "\n");
            if (data.getPBRDC() != null) {
                printer.printData(String.format("Broder Crop:%s ", data.getPBRDC()) + "\n");
            }
            if (data.getPCCND() != null) {
                printer.printData(String.format("Crop Condition:%s ", data.getPCCND()) + "\n");
            }
            if (data.getPCRPC() != null) {
                printer.printData(String.format("Crop Cycle:%s ", data.getPCRPC()) + "\n");
            }
            if (data.getPlseedflg() != null) {
                printer.printData(String.format("Crop Type:%s ", data.getPlseedflg()) + "\n");
            }
            if (data.getPDEMO() != null) {
                printer.printData(String.format("Demo Plot:%s ", data.getPDEMO()) + "\n");
            }
            if (data.getPDISE() != null) {
                printer.printData(String.format("Disease:%s ", data.getPDISE()) + "\n");
            }
            if (data.getPINSE() != null) {
                printer.printData(String.format("Insect:%s ", data.getPINSE()) + "\n");
            }
            if (data.getPINTC() != null) {
                printer.printData(String.format("Inter Cropping:%s ", data.getPINTC()) + "\n");
            }
            if (data.getPIRRG() != null) {
                printer.printData(String.format("Irrigation:%s ", data.getPIRRG()) + "\n");
            }
            if (data.getPLAND() != null) {
                printer.printData(String.format("Land Type:%s ", data.getPLAND()) + "\n");
            }
            if (data.getPMIXC() != null) {
                printer.printData(String.format("Mix Crop:%s ", data.getPMIXC()) + "\n");
            }
            if (data.getPSSRC() != null) {
                printer.printData(String.format("Seeds Source:%s ", data.getPSSRC()) + "\n");
            }
            if (data.getPSOIL() != null) {
                printer.printData(String.format("Soil Type:%s ", data.getPSOIL()) + "\n");
            }
            if (data.getPMTHD() != null) {
                printer.printData(String.format("Plant Method:%s ", data.getPMTHD()) + "\n");
            }
            printer.printData(String.format("Plot Vill:%s ", data.getPlotVill()) + "\n");
            printer.printData(String.format("Plot Serial:%s ", data.getPlSerial()) + "\n");
            // if (!gmlen.equalsIgnoreCase("5") && !gmlen.equalsIgnoreCase("6")) {
            printer.printData(String.format("Plot Area:%s ", data.getPlArea()) + "\n");
            //}
            printer.printData("                       " + "\n");
            printer.printData(String.format("EAST1:%s ", (int) data.getDims1() + " " + String.format("NORTH3:%s ", (int) data.getDims2())) + "\n");
            printer.printData(String.format("WEST2:%s ", (int) data.getDims3() + " " + String.format("SOUTH4:%s ", (int) data.getDims4())) + "\n");
            printer.printData("                       " + "\n");
            printer.printData(String.format("Grower Code:%s ", data.getGrowerCode()) + "\n");
            printer.printData(String.format("Village:%s ", data.getVillage()) + "\n");
            printer.printData(String.format("Grower:%s ", data.getGrowerName()) + "\n");
            printer.printData(String.format("Father:%s ", data.getGrFather()) + "\n");
            if (!gmlen.equalsIgnoreCase("5") && !gmlen.equalsIgnoreCase("6")) {
                printer.printData(String.format("Unicode:%s ", data.getUnicode()) + "\n");
            }
            printer.printData(String.format("Variety:%s ", data.getVariety()) + "\n");
            if (!gmlen.equalsIgnoreCase("5") && !gmlen.equalsIgnoreCase("6")) {
                printer.printData(String.format("Percentage:%s ", data.getPercent()) + "\n");
                printer.printData(String.format("Shared Area:%s ", data.getSharedArea()) + "\n");
            }

            printer.printData(String.format("Clerk:%s ", data.getClerkCode() + " " + uname) + "\n");
            printer.printData("                       " + "\n");
            printer.printData("                       " + "\n");
            printer.printData("                       " + "\n");
            printer.printData("                       " + "\n");
            printer.printData("                       " + "\n");
            printer.printData("                       " + "\n");
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public static void printSummary(String plots, String area, String ratoonArea, String ratoonPlots, String autumnArea, String autumnPlots, String plantArea, String plantPlots, String lastSerial, String lastVillCode, String lastVillName, String clerkCode, String uname, String gmlen, String date) {
        PrinterApi printer = new PrinterApi();
        printer.printData("   Survey Summary" + "\n");
        printer.printData(SEPARATOR);
        printer.printData(String.format("Date:%s ", date) + "\n");
        printer.printData(String.format("Total Plots :%s ", plots) + "\n");
        printer.printData(String.format("Total Area :%s ", area) + "\n");
        printer.printData(String.format("Last plot serial no :%s ", lastSerial) + "\n");
        printer.printData(String.format("Last plot village :%s ", lastVillCode + " " + lastVillName) + "\n");
        printer.printData(String.format("User:%s ", clerkCode + " " + uname) + "\n");
        printer.printData("Total     Plots     Area" + "\n");
        printer.printData("Ratoon    " + ratoonPlots + "     " + ratoonArea + "\n");
        printer.printData("Plant     " + plantPlots +  "      " + plantArea + "\n");
        if (!gmlen.equalsIgnoreCase("5") && !gmlen.equalsIgnoreCase("6")) {
            printer.printData("Autumn    " + autumnPlots + "     " + autumnArea + "\n");
        }
        printer.printData("                       " + "\n");
        printer.printData("                       " + "\n");
        printer.printData("                       " + "\n");
        printer.printData("                       " + "\n");
        printer.printData("                       " + "\n");
    }

    public static void printBlank() {
        PrinterApi printer = new PrinterApi();
        printer.printData("                       " + "\n");
        printer.printData("                       " + "\n");
        printer.printData("                       " + "\n");
        printer.printData("                       " + "\n");
        printer.printData("                       " + "\n");
        printer.printData("                       " + "\n");
        printer.printData("                       " + "\n");
        printer.printData("                       " + "\n");
        printer.printData("                       " + "\n");
        printer.printData("                       " + "\n");
        printer.printData("                       " + "\n");
        printer.printData("                       " + "\n");
    }

    private static void printLine(PrinterApi printer, Printer_2inch_Impact api, int number) {
        try {
            for (int i = 0; i < number; i++)
                printer.setPrintCommand(api.dotline_Feed());
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public static void printPlotRecheck(String date, String cropCycle, String cropType, String landType, String plotVill, String plSerial, String plArea, String dims1, String dims2, String dims3, String dims4, String growerCode, String village, String growerName, String variety, String clerkCode, String uname, String plotAreaRecheck) {
        try {
            PrinterApi printer = new PrinterApi();
            printer.printData("    Recheck Survey Slip" + "\n");
            printer.printData(SEPARATOR);
            printer.printData(String.format("Date:%s ", date) + "\n");
            printer.printData(String.format("Crop Cycle:%s ", cropCycle) + "\n");
            printer.printData(String.format("Crop Type:%s ", cropType) + "\n");
            printer.printData(String.format("Land Type:%s ", landType) + "\n");
            printer.printData(String.format("Plot Vill:%s ", plotVill) + "\n");
            printer.printData(String.format("Plot Serial:%s ", plSerial) + "\n");
            printer.printData(String.format("Plot Area:%s ", plArea) + "\n");
            printer.printData("                       " + "\n");
            printer.printData(String.format("EAST1:%s ", dims1 + " " + String.format("NORTH3:%s ", dims2)) + "\n");
            printer.printData(String.format("WEST2:%s ", dims3 + " " + String.format("SOUTH4:%s ", dims4)) + "\n");
            printer.printData("                       " + "\n");
            printer.printData(String.format("Grower Code:%s ", growerCode) + "\n");
            printer.printData(String.format("Village:%s ", village) + "\n");
            printer.printData(String.format("Grower:%s ", growerName) + "\n");
            printer.printData(String.format("Variety:%s ", variety) + "\n");
            printer.printData(String.format("User:%s ", clerkCode + " " + uname) + "\n");
            printer.printData(String.format("Recheck Plot Area:%s ", plotAreaRecheck) + "\n");
            printer.printData("                       " + "\n");
            printer.printData("                       " + "\n");
            printer.printData("                       " + "\n");
            printer.printData("                       " + "\n");
            printer.printData("                       " + "\n");
            printer.printData("                       " + "\n");
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public static void printVillageClosing(String closingDate, String plVill, String plVillName, String totalPlots, String totalArea, String plantPlots, String plantArea, String ratoonPlots, String ratoonArea, String startSerial, String lastSerial, String clerkCode,String uname) {
        try {
            PrinterApi printer = new PrinterApi();
            printer.printData("  Village Closing Slip\n" + "\n");
            printer.printData(SEPARATOR);
            printer.printData(String.format("Closing Date:%s ", closingDate) + "\n");
            printer.printData(String.format("Plot village:%s ", plVill) + "\n");
            printer.printData(String.format("Name:%s ", plVillName) + "\n");
            printer.printData(String.format("Total Plots:%s ", totalPlots) + "\n");
            printer.printData(String.format("Total Area:%s ", totalArea) + "\n");
            printer.printData(String.format("Plant Plots:%s ", plantPlots) + "\n");
            printer.printData(String.format("Plant Area:%s ", plantArea) + "\n");
            printer.printData(String.format("Ratoon Plots:%s ", ratoonPlots) + "\n");
            printer.printData(String.format("Ratoon  Area:%s ", ratoonArea) + "\n");
            printer.printData(String.format("Start Plsrno:%s ", startSerial) + "\n");
            printer.printData(String.format("Last Plsrno:%s ", lastSerial) + "\n");
            printer.printData(String.format("User:%s ", clerkCode + " " + uname) + "\n");
            printer.printData("                       " + "\n");
            printer.printData("                       " + "\n");
            printer.printData("                       " + "\n");
            printer.printData("                       " + "\n");
            printer.printData("                       " + "\n");
            printer.printData("                       " + "\n");
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }
}
