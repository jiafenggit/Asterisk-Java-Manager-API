package manager;

import java.util.ArrayList;
import java.util.List;

import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.action.CommandAction;
import org.asteriskjava.manager.response.CommandResponse;

/**
 * @author mbahhalim
 *
 */
public abstract class Manager {

	/**
	 * Send a CLI command and print the response
	 * 
	 * CLI command: sip show peers
	 * 
	 * @param managerConnection
	 * @throws Exception
	 */
	public static void sipShowPeers(ManagerConnection managerConnection) throws Exception {
//		Log In to Asterisk
		managerConnection.login();
		
		CommandAction action;
		CommandResponse response;
		
		List<String> strings = new ArrayList<String>();
		List<String> onlinePeers = new ArrayList<String>();
		List<String> offlinePeers = new ArrayList<String>();
		
//		Running CLI Command
		action = new CommandAction();
		action.setCommand("sip show peers");
		
//		Get Response
		response = (CommandResponse) managerConnection.sendAction(action);
		strings = response.getResult();
		
//		Print Response
		System.out.println(strings.get(0));
		int i = 1;
		while ( i < strings.size() - 1)
		{
			if (!strings.get(i).contains("(Unspecified)") &&
					strings.get(i).contains("D"))
				onlinePeers.add(strings.get(i));
			else
				offlinePeers.add(strings.get(i));
				
			System.out.println(strings.get(i));
			i++;
		}
		System.out.println(strings.get(strings.size() - 1));
		System.out.println();
		
//		Some Additional Printing
		for (String str : onlinePeers)
			System.out.println(str);
		System.out.println(onlinePeers.size() + " Online");
		System.out.println();
		
		for (String str : offlinePeers)
			System.out.println(str);
		System.out.println(offlinePeers.size() + " Offline");
		System.out.println();
		
//		Log Off from Asterisk
		managerConnection.logoff();
	}

    /**
	 * hostname: localhost
	 * username: mark
	 * password: mysecret
	 * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//    	Create a Manager Connection
    	ManagerConnection managerConnection =
    			new ManagerConnectionFactory(
    					"localhost",
    					"mark",
    					"mysecret").createManagerConnection();
    	
        Manager.sipShowPeers(managerConnection);
    }
}
