package br.ufrj.dcc.comp2.databinding.view;

import java.util.List;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.ufrj.dcc.comp2.databinding.controller.BindingController;
import br.ufrj.dcc.comp2.databinding.model.Person;

public class DatabindigAddUpdatePersonComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;

	java.util.List<String> titleModel = new java.util.ArrayList<String>();

	Textbox name;
	Combobox title;
	Datebox birthday;
	Textbox email;
	Image img;

	Window addUpdatePerson;

	Button AddUpdatePerson;

	String mode = null;
	Person person = null;

	BindingController bindingController = new BindingController();

	AnnotateDataBinder binder;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		comp.setAttribute(comp.getId() + "Ctrl", this, true);

		titleModel.add("Engineer");
		titleModel.add("Tester");
		titleModel.add("Manager");
		titleModel.add("Architect");
		titleModel.add("Professor");
		titleModel.add("Programmer");

		mode = (String) sessionScope.get("media.mode");

		if (mode.equals("add")) {
			AddUpdatePerson.setLabel("Add Person");

			sessionScope.remove("media.mode");

		} else {

			person = bindingController.getById(Person.class,
					(Integer) sessionScope.get("id"));

			if (person.getPicture() != null) {
				AImage picture = new AImage("picture", person.getPicture());
				img.setContent((org.zkoss.image.Image) picture);
			}

			AddUpdatePerson.setLabel("Update Person");

			addUpdatePerson.setTitle("Update Person");

			sessionScope.remove("media.mode");
		}

		AnnotateDataBinder binder = new AnnotateDataBinder(page);
		binder.loadAll();

	}

	public void onOK$addUpdatePerson(Event event) {
		submitOrUpdate(event);
	}

	public void onClick$AddUpdatePerson(Event event) {
		submitOrUpdate(event);
	}

	private void submitOrUpdate(Event event) {

		if (mode.equals("add")) {
			if (img.getContent() != null)
				person = new Person(name.getValue(), titleModel.get(title
						.getSelectedIndex()), email.getValue(), birthday
						.getValue(), ((AImage) img.getContent()).getByteData());
			else
				person = new Person(name.getValue(), titleModel.get(title
						.getSelectedIndex()), email.getValue(), birthday
						.getValue(), null);

			bindingController.addPerson(person);
			mode = null;

			EventQueues.lookup("comp2web", EventQueues.DESKTOP, true).publish(
					event);

		} else {
			person.setName(name.getValue());
			person.setTitle(titleModel.get(title.getSelectedIndex()));
			person.setEmail(email.getValue());
			if (img.getContent() != null)
				person.setPicture(((AImage) img.getContent()).getByteData());

			bindingController.update(person);
			mode = null;

			EventQueues.lookup("comp2web", EventQueues.DESKTOP, true).publish(
					event);

		}

		addUpdatePerson.detach();
	}

	public void onClick$returnPage(Event event) {

		addUpdatePerson.detach();
		mode = null;
	}

	public List<String> getTitleModel() {
		return titleModel;
	}

	public Person getPerson() {
		return person;
	}

}
