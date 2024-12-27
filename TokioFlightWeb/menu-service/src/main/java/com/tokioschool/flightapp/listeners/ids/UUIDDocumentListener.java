package com.tokioschool.flightapp.listeners.ids;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

import java.util.UUID;

/**
 * Cualquier clase o sublxase de UUIDDocument, pasara por este listener,
 * esto nos permitirá gestionar su ciclo de vida
 */
public class UUIDDocumentListener extends AbstractMongoEventListener<UUIDDocument> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<UUIDDocument> event) {
        UUIDDocument uuidDocument = event.getSource();

        // si es nulo, se lo inserta antes de convertilo a un documento.
        if(uuidDocument.getId() == null ){
            uuidDocument.setId( UUID.randomUUID() );
        }
    }
}
