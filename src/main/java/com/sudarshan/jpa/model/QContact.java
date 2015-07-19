package com.sudarshan.jpa.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QContact is a Querydsl query type for Contact
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QContact extends EntityPathBase<Contact> {

    private static final long serialVersionUID = -96540030L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContact contact = new QContact("contact");

    public final QAddress address;

    public final StringPath emailAddress = createString("emailAddress");

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath lastName = createString("lastName");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QContact(String variable) {
        this(Contact.class, forVariable(variable), INITS);
    }

    public QContact(Path<? extends Contact> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QContact(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QContact(PathMetadata<?> metadata, PathInits inits) {
        this(Contact.class, metadata, inits);
    }

    public QContact(Class<? extends Contact> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
    }

}

