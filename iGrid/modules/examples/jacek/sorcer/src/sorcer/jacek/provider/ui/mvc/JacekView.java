package sorcer.jacek.provider.ui.mvc;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import sorcer.util.Log;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.jini.core.lookup.ServiceItem;
import net.jini.lookup.entry.UIDescriptor;
import net.jini.lookup.ui.MainUI;
import sorcer.jacek.provider.Jacek;
import sorcer.jacek.provider.Operations;
import sorcer.core.provider.ServiceProvider;
import sorcer.ui.serviceui.UIComponentFactory;
import sorcer.ui.serviceui.UIDescriptorFactory;
import sorcer.util.Sorcer;

public class JacekView extends JPanel implements Observer {
	
	private static final long serialVersionUID = -3812646466769297683L;

	private JTextField resultTextField, baseTextField, exponentTextField;

	private JacekModel model;

	private JacekDispatcher dispatcher;

	private final static Logger logger = Log.getTestLog();

	public JacekView(Object provider) {
		super();
		getAccessibleContext().setAccessibleName("JacekView Tester");
		ServiceItem item = (ServiceItem) provider;

		if (item.service instanceof Jacek) {
			Jacek jacek = (Jacek) item.service;
			model = new JacekModel();
			dispatcher = new JacekDispatcher(model, this, jacek);
			createView();
			model.addObserver(this);
			dispatcher.getResult();
		}
	}

	protected void createView() {
		setLayout(new BorderLayout());
		add(buildJacekPanel(), BorderLayout.CENTER);
	}

	private JPanel buildJacekPanel() {
		JPanel panel = new JPanel();
		JPanel actionPanel = new JPanel(new GridLayout(2, 3));

		actionPanel.add(new JLabel("result:"));
		resultTextField = new JTextField();
		resultTextField.setEnabled(false);
		actionPanel.add(resultTextField);
		actionPanel.add(new JLabel(" "));

		actionPanel.add(new JLabel("base: "));
		baseTextField = new JTextField();
		actionPanel.add(baseTextField);
		actionPanel.add(new JLabel(" "));
		
		actionPanel.add(new JLabel("exponent: "));
		exponentTextField = new JTextField();
		actionPanel.add(exponentTextField);
		
		JButton calcButton = new JButton("Calc!");
		calcButton.setActionCommand("raisePower");
		calcButton.addActionListener(dispatcher);
		actionPanel.add(calcButton);

		panel.add(actionPanel);
		return panel;
	}

	/*public Money getDepositAmount() {
		return readTextField(depositTextField);
	}

	public Money getWithdrawalAmount() {
		return readTextField(withdrawalTextField);
	}

	public void clearDepositAmount() {
		depositTextField.setText("");
	}

	public void clearWithdrawalAmount() {
		withdrawalTextField.setText("");
	}

	public void displayBalance() {
		Money balance = model.getBalance();
		balanceTextField.setText(balance.value());
	}
	*/

	private double readTextField(JTextField valueField) {
		try {
			double doubleValue = Double.parseDouble(valueField.getText());
			//float actualValue = floatValue.floatValue();
			//int cents = (int) (actualValue * 100);
			//return new Money(cents);
			return doubleValue;
		} 
		catch (Exception e) {
			logger.info("Field doesn't contain a valid value");
		}
		return -1.0;
	}
	
	public void update(Observable o, Object arg) {
		logger.info("update>>arg: " + arg);
		/*if (arg != null) {
			if (arg.equals(JacekModel.DEPOSIT))
				clearDepositAmount();
			else if (arg.equals(JacekModel.WITHDRAW))
				clearWithdrawalAmount();
			else if (arg.equals(JacekModel.BALANCE))
				displayBalance();
		}*/
	}

	/**
	 * Returns a service UI descriptorfor this service. Usally this method is
	 * used as an entry in provider configuration files when smart proxies are
	 * deployed with a standard off the shelf {@link ServiceProvider}.
	 * 
	 * @return service UI descriptor
	 */
	public static UIDescriptor getUIDescriptor() {
		UIDescriptor uiDesc = null;
		try {
			uiDesc = UIDescriptorFactory.getUIDescriptor(MainUI.ROLE,
					new UIComponentFactory(new URL[] { new URL(Sorcer
							.getWebsterUrl()
							+ "/accout-mvc-ui.jar") }, JacekView.class
							.getName()));
		} catch (Exception ex) {
			logger.throwing(JacekView.class.getName(), "getUIDescriptor", ex);
		}
		return uiDesc;
	}

}
