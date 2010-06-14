package br.ufrj.dcc.comp2.databinding.view;

import java.awt.Button;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.zkoss.image.AImage;
import org.zkoss.image.Images;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.ufrj.dcc.comp2.databinding.controller.BindingController;
import br.ufrj.dcc.comp2.databinding.model.Person;

public class DatabindingComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;

	List<Person> model = new ArrayList<Person>();

	Listbox list;
	Button addPerson;

	Window showPicture;

	BindingController bindingController = new BindingController();

	AnnotateDataBinder binder;

	public DatabindingComposer() {
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute(comp.getId() + "Ctrl", this, true);

		for (Person person : bindingController.getAll(Person.class)) {
			model.add(person);
		}

		EventQueues.lookup("comp2web", EventQueues.DESKTOP, true).subscribe(
				new EventListener() {

					public void onEvent(Event event) throws Exception {
						model.clear();
						model.addAll(bindingController.getAll(Person.class));

						binder.saveAll();
						binder.loadAll();
					}
				});

	}

	public void onClick$delete(Event event) {

		try {
			if (Messagebox.show("Are you sure?", "Delete confirmation",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {

				ForwardEvent fe = (ForwardEvent) event;

				Listitem listItem = (Listitem) fe.getOrigin().getTarget()
						.getParent().getParent();
				Integer idListCell = Integer.parseInt(listItem.getFirstChild()
						.getId());

				Iterator<Person> itPerson = model.iterator();

				while (itPerson.hasNext()) {
					Person person = itPerson.next();
					if (person.getId().equals(idListCell)) {
						bindingController.remove(person);
						model.remove(person);
						break;
					}
				}

				EventQueues.lookup("comp2web", EventQueues.DESKTOP, true)
						.publish(event);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void onClick$edit(Event event) {

		ForwardEvent fe = (ForwardEvent) event;

		Listitem listItem = (Listitem) fe.getOrigin().getTarget().getParent()
				.getParent();

		Integer idListCell = Integer.parseInt(listItem.getFirstChild().getId());

		Sessions.getCurrent().setAttribute("id", idListCell);
		Sessions.getCurrent().setAttribute("media.mode", "update");

		Window add_update = (Window) Executions.createComponents(
				"add-update-person.zul", null, null);

		try {
			add_update.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void onClick$addPerson(Event event) {

		Sessions.getCurrent().setAttribute("media.mode", "add");

		Window add_update = (Window) Executions.createComponents(
				"add-update-person.zul", null, null);

		try {
			add_update.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void onClick$picture(Event event) {

		ForwardEvent fe = (ForwardEvent) event;

		Listitem listItem = (Listitem) fe.getOrigin().getTarget().getParent()
				.getParent();
		Integer idListCell = Integer.parseInt(listItem.getFirstChild().getId());

		Person person = bindingController.getById(Person.class, idListCell);
		Image img = (Image) showPicture.getFellow("img");

		if (person.getPicture() != null) {
			AImage picture;
			try {
				picture = new AImage("picture", person.getPicture());
				img.setContent((org.zkoss.image.Image) picture);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {

				InputStream in = desktop.getWebApp().getResourceAsStream(
						"img/defaulticon.jpg");

				RenderedImage im = ImageIO.read(in);

				AImage aImg = (AImage) Images.encode("picture.png",
						(RenderedImage) im);
				img.setContent(aImg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			showPicture.setTitle("Picture of " + person.getName());
			showPicture.doModal();
		} catch (SuspendNotAllowedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public List<Person> getModel() {
		return model;
	}

}
