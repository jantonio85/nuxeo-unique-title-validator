/**
 *
 */
package org.athento.nuxeo.validator;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.nuxeo.ecm.automation.jsf.OperationHelper;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.impl.DocumentModelImpl;
import org.nuxeo.ecm.core.query.sql.NXQL;


/**
 *
 *
 * @author athento
 *
 */
@Name("AthentoValidatorTitle")
public class FieldValidatorBeanTitle implements Serializable {

	/**
     *
     */
    private static final long serialVersionUID = 1L;



    @In(create = true, required = false)
    protected transient CoreSession documentManager;
    protected transient DocumentModelImpl doc1;




    protected String lang = NXQL.NXQL;

    private DocumentModelList documentModelList;

    public void validateuniquetitle(FacesContext context, UIComponent component, Object value) throws Exception{
        /**
        *   Funcionalidad que evita que se creen documentos con el mismo título
        */


        StringBuilder miquery = new StringBuilder();
        miquery.append("SELECT * FROM Document WHERE dc:title = '");
        miquery.append(value);
        miquery.append("' AND ecm:mixinType != 'HiddenInNavigation' AND ecm:currentLifeCycleState != 'deleted'");

        String q = miquery.toString();

        DocumentModelList docList;

        docList=documentManager.query(q);

        int i=docList.size();

        if (i!=0){
            String info_message = "Documento '"+value+"' existente";
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, info_message, null);
                throw new ValidatorException(message);
        }


	}

    public void validateuniquetitlemod(FacesContext context, UIComponent component, Object value) throws Exception{
        /**
        *   Funcionalidad que evita que se creen documentos con el mismo título una vez modificados
        */

        String id_doc= OperationHelper.getNavigationContext().getCurrentDocument().getId();
        StringBuilder miquery = new StringBuilder();
        miquery.append("SELECT * FROM Document WHERE dc:title = '");
        miquery.append(value);
        miquery.append("' AND ecm:uuid != '");
        miquery.append(id_doc);
        miquery.append("' AND ecm:mixinType != 'HiddenInNavigation' AND ecm:currentLifeCycleState != 'deleted'");

        String q = miquery.toString();

        DocumentModelList docList;


        docList=documentManager.query(q);



        int i=docList.size();

        if (i!=0){
            String info_message = "Documento '"+value+"' existente";
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, info_message, null);
                throw new ValidatorException(message);
        }


    }




}
