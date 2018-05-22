package naga.fx.spi.javafx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import naga.fx.spi.Toolkit;
import naga.platform.services.uischeduler.spi.impl.UiSchedulerProviderBase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Bruno Salmon
 */
class FxSchedulerProvider extends UiSchedulerProviderBase {

    static FxSchedulerProvider SINGLETON = new FxSchedulerProvider();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private FxSchedulerProvider() {
    }

    @Override
    public void runInBackground(Runnable runnable) {
        executor.execute(runnable);
    }

    @Override
    public void scheduleDeferred(Runnable runnable) {
        Toolkit toolkit = Toolkit.get();
        if (!toolkit.isReady())
            toolkit.onReady(runnable);
        else
            Platform.runLater(runnable);
    }

    @Override
    public boolean isUiThread() {
        return Toolkit.get().isReady() && Platform.isFxApplicationThread();
    }

    @Override
    public long nanoTime() {
        return System.nanoTime();
    }

    private Timeline timeline;

    @Override
    protected void checkExecuteAnimationPipeIsScheduledForNextAnimationFrame() {
        synchronized (this) {
            if (timeline == null) {
                timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> this.executeAnimationPipe()));
                timeline.setCycleCount(Integer.MAX_VALUE);
                timeline.play();
            }
        }
    }

    @Override
    protected void onExecuteAnimationPipeFinished(boolean noMoreAnimationScheduled) {
        synchronized (this) {
            if (noMoreAnimationScheduled && timeline != null) {
                timeline.stop();
                timeline = null;
            }
        }
    }
}
