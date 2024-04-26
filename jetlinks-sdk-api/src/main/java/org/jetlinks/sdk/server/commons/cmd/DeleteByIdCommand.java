package org.jetlinks.sdk.server.commons.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.StringType;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author zhouhao
 * @since 2.1
 */
public class DeleteByIdCommand<T> extends OperationByIdCommand<T, DeleteByIdCommand<T>> {

    public static FunctionMetadata metadata(Consumer<SimpleFunctionMetadata> custom) {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        //DeleteById
        metadata.setId(CommandUtils.getCommandIdByType(DeleteByIdCommand.class));
        metadata.setName("根据id删除");
        metadata.setInputs(Collections.singletonList(SimplePropertyMetadata.of("id", "Id", StringType.GLOBAL)));
        custom.accept(metadata);
        return metadata;
    }

    public static <T> CommandHandler<DeleteByIdCommand<Mono<T>>, Mono<T>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<DeleteByIdCommand<Mono<T>>, Mono<T>> handler,
        ResolvableType elementType) {
        return createHandler(custom, handler, CommandUtils.createConverter(elementType));
    }

    public static <T> CommandHandler<DeleteByIdCommand<Mono<T>>, Mono<T>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<DeleteByIdCommand<Mono<T>>, Mono<T>> handler,
        Function<Object, T> resultConverter) {

        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            () -> new DeleteByIdCommand<Mono<T>>().withConverter(resultConverter)
        );
    }

    public static <T> CommandHandler<DeleteByIdCommand<T>, T> createHandler(Consumer<SimpleFunctionMetadata> custom,
                                                                            Function<DeleteByIdCommand<T>, T> handler) {


        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            DeleteByIdCommand::new
        );

    }


}
