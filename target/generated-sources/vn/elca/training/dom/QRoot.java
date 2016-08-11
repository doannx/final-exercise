package vn.elca.training.dom;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRoot is a Querydsl query type for Root
 */
@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QRoot extends EntityPathBase<Root> {

    private static final long serialVersionUID = 1061556815L;

    public static final QRoot root = new QRoot("root");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QRoot(String variable) {
        super(Root.class, forVariable(variable));
    }

    public QRoot(Path<? extends Root> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoot(PathMetadata<?> metadata) {
        super(Root.class, metadata);
    }

}

