package com.lx.demo.repository;

import com.lx.demo.domain.User;
import cz.jirutka.rsql.parser.ast.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.List;

public class CustompRsqlVisitor<T> implements RSQLVisitor<Specification<User>, Void> {

    private UserRsqlSpecBuilder builder;

    public CustompRsqlVisitor() {
        builder = new UserRsqlSpecBuilder();
    }

    public Specification<User> visit(AndNode node, Void param) {
        return builder.createSpecification(node);
    }

    public Specification<User> visit(OrNode node, Void param) {
        return builder.createSpecification(node);
    }

    public Specification<User> visit(ComparisonNode node, Void params) {
//        return builder.createSecification(node);
        return null;
    }

    class UserRsqlSpecBuilder {

        public Specifications<User> createSpecification(Node node) {
            if (node instanceof LogicalNode) {
                return createSpecification((LogicalNode) node);
            }
            if (node instanceof ComparisonNode) {
                return createSpecification((ComparisonNode) node);
            }
            return null;
        }

        public Specifications<User> createSpecification(LogicalNode logicalNode) {
            List<Specifications<User>> specs = new ArrayList<Specifications<User>>();
            Specifications<User> temp;
            for (Node node : logicalNode.getChildren()) {
                temp = createSpecification(node);
                if (temp != null) {
                    specs.add(temp);
                }
            }

            Specifications<User> result = specs.get(0);
            if (logicalNode.getOperator() == LogicalOperator.AND) {
                for (int i = 1; i < specs.size(); i++) {
                    result = Specifications.where(result).and(specs.get(i));
                }
            } else if (logicalNode.getOperator() == LogicalOperator.OR) {
                for (int i = 1; i < specs.size(); i++) {
                    result = Specifications.where(result).or(specs.get(i));
                }
            }

            return result;
        }

        public Specifications<User> createSpecification(ComparisonNode comparisonNode) {
            Specifications<User> result = Specifications.where(
                    new UserRsqlSpecification(
                            comparisonNode.getSelector(),
                            comparisonNode.getOperator(),
                            comparisonNode.getArguments()
                    )
            );
            return result;
        }
    }
}
