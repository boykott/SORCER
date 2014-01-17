package sorcer.jacek.provider.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.logging.Logger;

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

public class JacekUI extends JPanel {

	private static final long serialVersionUID = -3171243785170712405L;

	private JTextField resultTextField, baseTextField, exponentTextField;

	private Jacek jacek;

	private ServiceItem item;

	private final static Logger logger = Logger.getLogger(JacekUI.class.getName());

	public JacekUI(Object provider) {
		super();
		getAccessibleContext().setAccessibleName("Jacek Tester");
		item = (ServiceItem) provider;
		

		if (item.service instanceof Jacek) {
			jacek = (Jacek) item.service;
			createUI();
		}
		
		logger.info("[JACEK] JacekUI started");

	}

	protected void createUI() {
		setLayout(new BorderLayout());
		add(buildJacekPanel(), BorderLayout.CENTER);
		//resetBalanceField();
	}

	/*private void resetBalanceField() {
		try {
			Money balance = jacek.getBalance();
			balanceTextField.setText(balance.value());
		} catch (Exception e) {
			logger.info("Error occurred while getting jacek balance");
			logger.throwing(getClass().getName(), "resetBalanceField", e);
		}
	}
	*/
	
	/*private void resetResultField() {
		try {
			double res = jacek.getResult();
			resultTextField.setText(res.toString() );
		} 
		catch (Exception e) {
			logger.info("Error occurred while getting jacek result");
			logger.throwing(getClass().getName(), "resultBalanceField", e);
		}
	}
	*/

	private JPanel buildJacekPanel() {
		JPanel panel = new JPanel();
		JPanel actionPanel = new JPanel(new GridLayout(3, 2));

		actionPanel.add(new JLabel("Result:"));
		resultTextField = new JTextField();
		resultTextField.setEnabled(false);
		resultTextField.setText("0.000");
		actionPanel.add(resultTextField);
		actionPanel.add(new JLabel(" "));

		actionPanel.add(new JLabel("Base val: "));
		baseTextField = new JTextField();
		actionPanel.add(baseTextField);
		
		actionPanel.add(new JLabel("Exponent val: "));
		exponentTextField = new JTextField();
		actionPanel.add(exponentTextField);
		JButton calcButton = new JButton("do it");
		calcButton.addActionListener(new CalcAction());
		actionPanel.add(calcButton);

		panel.add(actionPanel);
		return panel;
	}

	/*private Money readTextField(JTextField moneyField) {
		try {
			Float floatValue = new Float(moneyField.getText());
			float actualValue = floatValue.floatValue();
			int cents = (int) (actualValue * 100);

			return new Money(cents);
		} catch (Exception e) {
			logger.info("Field doesn't contain a valid value");
		}
		return null;
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


	/*private class WithdrawAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				Money withdrawalAmount = readTextField(withdrawalTextField);
				jacek.makeWithdrawal(withdrawalAmount);
				withdrawalTextField.setText("");
				resetBalanceField();
			} catch (Exception exception) {
				logger.info("Couldn't talk to jacek. Error was" + exception);
				logger.throwing(getClass().getName(), "actionPerformed",
						exception);
			}
		}
	}
	*/
	
	private class CalcAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				double baseAmount = readTextField(baseTextField);
				double exponentAmount = readTextField(exponentTextField);
				
				jacek.raisePower(exponentAmount);
				//withdrawalTextField.setText("");
				//resetBalanceField();
				//resetResultField();
				
				logger.info(getClass().getName()+" ; base val: "+baseAmount+" ; exponent val: "+exponentAmount );
			} 
			catch (Exception exception) {
				logger.info("Couldn't talk to jacek. Error was" + exception);
				logger.throwing(getClass().getName(), "actionPerformed", exception);
			}
		}
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
							+ "/accout-ui.jar") }, JacekUI.class.getName()));
		} catch (Exception ex) {
			logger.throwing(JacekUI.class.getName(), "getUIDescriptor", ex);
		}
		return uiDesc;
	}
}
