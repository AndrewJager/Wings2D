package com.wings2d.framework.imageFilters;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

public abstract class FilterEdit<T extends ImageFilter> extends JDialog{
	private static final long serialVersionUID = 1L;
	private JButton okBtn;
	private ImageFilter newFilter;
	
	// Insert
	public FilterEdit(final Frame owner)
	{
		super(owner, true);
		this.setLayout(new FlowLayout());
		this.setSize(new Dimension(200, 200));
		setup();
		addButtons();
	}
	
	// Update
	public FilterEdit(final T filter, final Frame owner) {
		this(owner);
		setValues(filter);
	}
	
	protected void addButtons()
	{
		okBtn = new JButton("Ok");
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newFilter = getFilter();
				setVisible(false);
				dispose();
			}
		});
		this.add(okBtn);
	}
	
	public ImageFilter showDialog()
	{
		setVisible(true);
		return newFilter;
	}
	
	public abstract void setup();
	public abstract void setValues(final T filter);
	public abstract ImageFilter getFilter();
}
