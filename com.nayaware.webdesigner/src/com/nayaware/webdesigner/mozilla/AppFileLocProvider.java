package com.nayaware.webdesigner.mozilla;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.mozilla.xpcom.IAppFileLocProvider;

import com.nayaware.webdesigner.WebDesignerPlugin;

/**
 * @author Winston Prakash
 * @version 1.0
 */
public class AppFileLocProvider implements IAppFileLocProvider {
	private File xulRunnerPath;
	private File userDataPath;
	
	private static final String PLUGINS_DIRECTORY = "plugins"; // $NON-NLS-1$
	private static final String HISTORY_FILE = "history.dat"; // $NON-NLS-1$
	private static final String COMPREG_FILE = "compreg.dat"; // $NON-NLS-1$
	private static final String XPTI_FILE = "xpti.dat"; // $NON-NLS-1$
	private static final String COMPONENTS_DIRECTORY = "components"; //$NON-NLS-1$
	
	public static final boolean DEBUG_BROWSERSTART = true;
	
	public AppFileLocProvider(File xulRunnerPath) {
		this.xulRunnerPath = xulRunnerPath;
		this.userDataPath = Platform.getLocation().append(".metadata/.plugins") // $NON-NLS-1$
				.append(WebDesignerPlugin.PLUGIN_ID)
				.append("xulrunner").toFile(); // $NON-NLS-1$
	}
	
	public File getFile(String prop, boolean[] persistent) {
		persistent[0] = false;
		
		File resultFile = null;
		
		if ("ProfD".equals(prop)) { // $NON-NLS-1$
			resultFile = userDataPath;
		} else if ("UHist".equals(prop)) { // $NON-NLS-1$
			resultFile = new File(userDataPath, HISTORY_FILE); 
		} else if ("ComRegF".equals(prop)) { // $NON-NLS-1$
			resultFile = new File(userDataPath, COMPREG_FILE);
		} else if ("XptiRegF".equals(prop)) { // $NON-NLS-1$
			resultFile = new File(userDataPath, XPTI_FILE);
		} else if ("GreD".equals(prop)) { // $NON-NLS-1$
			resultFile = xulRunnerPath;
		} else if ("GreComsD".equals(prop) || "ComsD".equals(prop)) { // $NON-NLS-1$ // $NON-NLS-1$
			resultFile = new File(xulRunnerPath, COMPONENTS_DIRECTORY);
		}

		if (DEBUG_BROWSERSTART) {
			System.out.println("AppFileLocProvider.getFile(" + prop + ")=\"" + (resultFile == null ? "null" : resultFile.toString()) + "\"");
		}
		
		return resultFile;
	}

	public File[] getFiles(String prop) {
		File[] resultFiles = null;
		if("APluginsDL".equals(prop)) { // $NON-NLS-1$
			resultFiles = new File[] {new File(xulRunnerPath, PLUGINS_DIRECTORY)};
		}
		
		if (DEBUG_BROWSERSTART) {
			if (resultFiles != null) {
				int size = resultFiles.length;

				System.out.print("AppFileLocProvider.getFiles(" + prop + ")={\"");
				for(int i=0; i<size; i++) {
					System.out.print((i > 0 ? ", " : "") + resultFiles[i].toString());
				}
				System.out.println("\"}");
			}
		}
		
		return resultFiles;
	}

}
