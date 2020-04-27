package ru.agiletech.sprint.service.presentation;

import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

import java.lang.reflect.Field;

@Component
public class LinksHideHandler implements ModelPropertyBuilderPlugin {

    @Override
    public void apply(ModelPropertyContext context) {
        if (context.getBeanPropertyDefinition().isPresent()) {

            BeanPropertyDefinition beanPropertyDefinition = context.getBeanPropertyDefinition().get();
            AnnotatedField annotatedField = beanPropertyDefinition.getField();

            if (null != annotatedField) {
                Field field = (Field) annotatedField.getMember();

                if (field != null)
                    hideLinksField(context, field);
            }

        } else if (context.getAnnotatedElement().isPresent()
                && context.getAnnotatedElement().get() instanceof Field) {

            Field field = (Field) context.getAnnotatedElement().get();
            hideLinksField(context, field);
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }

    private void hideLinksField(final ModelPropertyContext  context,
                                final Field                 field) {
        if (RepresentationModel.class.equals(field.getDeclaringClass())
                && field.getName().contains("links")) {

            context.getBuilder().isHidden(true);
        }
    }

}
