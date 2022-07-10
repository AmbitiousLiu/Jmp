package io.github.ambitiousliu.jmp.conditions;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.conditions.SharedString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ambitious liu
 * @since 2022/7/10
 */
public class LambdaJoinWrapper<T> extends AbstractLambdaJoinWrapper<T, LambdaJoinWrapper<T>> {

    public LambdaJoinWrapper(String prefix, AbstractJoinWrapper<?, ?, ?> targetWrapper) {
        this(prefix, (T) null, targetWrapper);
    }

    public LambdaJoinWrapper(String prefix, T entity, AbstractJoinWrapper<?, ?, ?> targetWrapper) {
        super(prefix, targetWrapper);
        super.setJoinEntity(entity);
        super.initNeed();
    }

    public LambdaJoinWrapper(String prefix, Class<T> entityClass, AbstractJoinWrapper<?, ?, ?> targetWrapper) {
        super(prefix, targetWrapper);
        super.setEntityClass(entityClass);
        super.initNeed();
    }

    LambdaJoinWrapper(T entity, Class<T> entityClass, AtomicInteger paramNameSeq,
                       Map<String, Object> paramNameValuePairs, List<ISqlSegment[]> sqlSegmentList,
                       SharedString lastSql, SharedString sqlComment, SharedString sqlFirst) {
        super("", null);
        super.setJoinEntity(entity);
        super.setEntityClass(entityClass);
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.sqlSegmentList = sqlSegmentList;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
        this.sqlFirst = sqlFirst;
    }

    @Override
    protected LambdaJoinWrapper<T> instance() {
        return new LambdaJoinWrapper<>(getJoinEntity(), getEntityClass(), paramNameSeq, paramNameValuePairs,
                new ArrayList<>(), SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString());
    }
}
