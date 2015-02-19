package net.etfbl.musicfever.utils;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import net.etfbl.musicfever.beans.*;

public class AuthPhaseListener implements PhaseListener {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		ExternalContext context = facesContext.getExternalContext();
		context.getSession(true);
		Map sessMap = context.getSessionMap();

		UserBean userBean = (UserBean) sessMap.get("userBean");

		boolean onIndex = (-1 != facesContext.getViewRoot().getViewId()
				.lastIndexOf("index")) ? true : false;
		boolean onAccount = (-1 != facesContext.getViewRoot().getViewId()
				.lastIndexOf("account")) ? true : false;
		boolean onRegistrationPage = (-1 != facesContext.getViewRoot().getViewId()
				.lastIndexOf("registration")) ? true : false;
		boolean onPrikaziSlikuPage = (-1 != facesContext.getViewRoot().getViewId()
				.lastIndexOf("prikaziSliku")) ? true : false;
		
		if (!onIndex && ((userBean == null) || !(userBean.isLoggedIn())) && !onAccount && !onRegistrationPage && !onPrikaziSlikuPage) {
			event.getFacesContext().getApplication().getNavigationHandler()
					.handleNavigation(event.getFacesContext(), null, "pocetna");
		}
		/*boolean onAdminPage = (-1 != facesContext.getViewRoot().getViewId()
				.lastIndexOf("admin")) ? true : false;
		boolean onAzurirajDestinacijuPage = (-1 != facesContext.getViewRoot()
				.getViewId().lastIndexOf("azurirajDestinaciju")) ? true : false;
		boolean onDestinacijePage = (-1 != facesContext.getViewRoot()
				.getViewId().lastIndexOf("destinacije")) ? true : false;
		boolean onDestinacijePoMjesecimaPage = (-1 != facesContext
				.getViewRoot().getViewId()
				.lastIndexOf("destinacijePoMjesecima")) ? true : false;
		boolean onDodajDestinacijuPage = (-1 != facesContext.getViewRoot()
				.getViewId().lastIndexOf("dodajDestinaciju")) ? true : false;
		boolean onDodajTipDestinacijePage = (-1 != facesContext.getViewRoot()
				.getViewId().lastIndexOf("dodajTipDestinacije")) ? true : false;
		boolean onPregledRegistracijaPage = (-1 != facesContext.getViewRoot()
				.getViewId().lastIndexOf("pregledRegistracija")) ? true : false;
		boolean onRegistracijePoMjesecimaPage = (-1 != facesContext
				.getViewRoot().getViewId()
				.lastIndexOf("registracijePoMjesecima")) ? true : false;

		if (userBean != null) {
			if (userBean.isLoggedIn()
					&& !(userBean.getUser().getUsergroup() == 1)
					&& (onAdminPage || onAzurirajDestinacijuPage
							|| onDestinacijePage
							|| onDestinacijePoMjesecimaPage
							|| onDodajDestinacijuPage
							|| onDodajTipDestinacijePage
							|| onPregledRegistracijaPage || onRegistracijePoMjesecimaPage)) {
				event.getFacesContext()
						.getApplication()
						.getNavigationHandler()
						.handleNavigation(event.getFacesContext(), null, "registrovani");
			}
		}*/
	}

	public void beforePhase(PhaseEvent event) {

	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
