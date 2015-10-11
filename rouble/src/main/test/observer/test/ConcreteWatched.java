package observer.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: haolu
 * Date: 15-10-8
 * Time: ÏÂÎç2:58
 * To change this template use File | Settings | File Templates.
 */
public class ConcreteWatched implements  Watched {
    private List<Watcher> list = new ArrayList<Watcher>();
    @Override
    public void addWatcher(Watcher watcher) {
        list.add(watcher);
    }

    @Override
    public void removeWatcher(Watcher watcher) {
        list.remove(watcher);
    }

    @Override
    public void notifyWatchers(String str) {
        for(Watcher watcher : list){
            watcher.update(str);
        }
    }
}
