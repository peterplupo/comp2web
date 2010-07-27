package br.ufrj.dcc.comp2.databinding.view.converters;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class BirthdayConverter implements TypeConverter {

	public Object coerceToBean(Object val, Component comp) {
		return null;
	}

	public Object coerceToUi(Object val, Component comp) {

		Date birthday = (Date) val;

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		String formatedBirthday = format.format(birthday);

		return formatedBirthday;
	}

}
