package com.android.ahat.dominators;

import com.android.ahat.progress.NullProgress;
import com.android.ahat.progress.Progress;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Queue;

public class Dominators<Node> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Graph<Node> graph;
    private long numNodes = 0;
    private Progress progress = new NullProgress();

    public interface Graph<Node> {
        Object getDominatorsComputationState(Node node);

        Iterable<? extends Node> getReferencesForDominators(Node node);

        void setDominator(Node node, Node node2);

        void setDominatorsComputationState(Node node, Object obj);
    }

    public Dominators(Graph graph2) {
        this.graph = graph2;
    }

    public Dominators progress(Progress progress2, long numNodes2) {
        this.progress = progress2;
        this.numNodes = numNodes2;
        return this;
    }

    private static class NodeS {
        public long depth;
        public NodeS domS;
        public NodeSet dominated;

        /* renamed from: id */
        public long f39id;
        public IdSet inRefIds;
        public long maxReachableId;
        public Object node;
        public NodeS oldDomS;
        public NodeSet revisit;

        private NodeS() {
            this.inRefIds = new IdSet();
            this.dominated = new NodeSet();
            this.revisit = null;
        }
    }

    private static class IdSet {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private long[] ids;
        /* access modifiers changed from: private */
        public int size;

        static {
            Class<Dominators> cls = Dominators.class;
        }

        private IdSet() {
            this.size = 0;
            this.ids = new long[4];
        }

        public void add(long id) {
            int i = this.size;
            long[] jArr = this.ids;
            if (i == jArr.length) {
                this.ids = Arrays.copyOf(jArr, i * 2);
            }
            long[] jArr2 = this.ids;
            int i2 = this.size;
            this.size = i2 + 1;
            jArr2[i2] = id;
        }

        public long last() {
            return this.ids[this.size - 1];
        }

        public boolean hasIdInRange(long low, long high) {
            for (int i = 0; i < this.size; i++) {
                long[] jArr = this.ids;
                if (low <= jArr[i] && jArr[i] <= high) {
                    return true;
                }
            }
            return false;
        }
    }

    private static class NodeSet {
        public NodeS[] nodes;
        public int size;

        private NodeSet() {
            this.size = 0;
            this.nodes = new NodeS[4];
        }

        public void add(NodeS nodeS) {
            int i = this.size;
            NodeS[] nodeSArr = this.nodes;
            if (i == nodeSArr.length) {
                this.nodes = (NodeS[]) Arrays.copyOf(nodeSArr, i * 2);
            }
            NodeS[] nodeSArr2 = this.nodes;
            int i2 = this.size;
            this.size = i2 + 1;
            nodeSArr2[i2] = nodeS;
        }

        public void remove(NodeS nodeS) {
            for (int i = 0; i < this.size; i++) {
                if (this.nodes[i] == nodeS) {
                    remove(i);
                    return;
                }
            }
        }

        public void remove(int index) {
            NodeS[] nodeSArr = this.nodes;
            int i = this.size - 1;
            this.size = i;
            nodeSArr[index] = nodeSArr[i];
            nodeSArr[this.size] = null;
        }
    }

    private static class Link<Node> {
        public final Node dst;
        public final NodeS srcS;

        public Link(NodeS srcS2, Node dst2) {
            this.srcS = srcS2;
            this.dst = dst2;
        }

        public Link(NodeS srcS2) {
            this.srcS = srcS2;
            this.dst = null;
        }
    }

    public void computeDominators(Node root) {
        long id;
        long j;
        Deque<Link<Node>> dfs;
        Node node = root;
        Queue<NodeS> revisit = new ArrayDeque<>();
        C07661 r6 = null;
        NodeS rootS = new NodeS();
        rootS.node = node;
        long j2 = 1;
        long id2 = 0 + 1;
        rootS.f39id = 0;
        rootS.depth = 0;
        this.graph.setDominatorsComputationState(node, rootS);
        Deque<Link<Node>> dfs2 = new ArrayDeque<>();
        dfs2.push(new Link(rootS));
        for (Node child : this.graph.getReferencesForDominators(node)) {
            dfs2.push(new Link(rootS, child));
        }
        long workBound = 0;
        this.progress.start("Initializing dominators", this.numNodes);
        while (!dfs2.isEmpty()) {
            Link<Node> link = dfs2.pop();
            if (link.dst == null) {
                link.srcS.maxReachableId = id2 - j2;
                this.progress.advance();
                dfs = dfs2;
                j = j2;
            } else {
                NodeS dstS = (NodeS) this.graph.getDominatorsComputationState(link.dst);
                if (dstS == null) {
                    NodeS dstS2 = new NodeS();
                    this.graph.setDominatorsComputationState(link.dst, dstS2);
                    dstS2.node = link.dst;
                    long id3 = id2 + j2;
                    dstS2.f39id = id2;
                    dstS2.inRefIds.add(link.srcS.f39id);
                    dstS2.domS = link.srcS;
                    dstS2.domS.dominated.add(dstS2);
                    dstS2.oldDomS = link.srcS;
                    j = 1;
                    dstS2.depth = link.srcS.depth + 1;
                    dfs2.push(new Link(dstS2));
                    for (Node child2 : this.graph.getReferencesForDominators(link.dst)) {
                        dfs2.push(new Link(dstS2, child2));
                    }
                    id2 = id3;
                    dfs = dfs2;
                } else {
                    j = j2;
                    if (dstS.inRefIds.size == 1) {
                        workBound += dstS.oldDomS.depth;
                    }
                    long seenid = dstS.inRefIds.last();
                    dfs = dfs2;
                    dstS.inRefIds.add(link.srcS.f39id);
                    NodeS xS = link.srcS;
                    while (true) {
                        Link<Node> link2 = link;
                        if (xS.f39id <= seenid) {
                            break;
                        }
                        xS = xS.domS;
                        link = link2;
                    }
                    long domid = xS.f39id;
                    if (dstS.domS.f39id > domid) {
                        if (dstS.domS == dstS.oldDomS) {
                            if (dstS.oldDomS.revisit == null) {
                                dstS.oldDomS.revisit = new NodeSet();
                                revisit.add(dstS.oldDomS);
                            }
                            dstS.oldDomS.revisit.add(dstS);
                        }
                        dstS.domS.dominated.remove(dstS);
                        do {
                            dstS.domS = dstS.domS.domS;
                        } while (dstS.domS.f39id > domid);
                        dstS.domS.dominated.add(dstS);
                    }
                }
            }
            dfs2 = dfs;
            j2 = j;
            r6 = null;
        }
        this.progress.done();
        this.progress.start("Resolving dominators", workBound);
        while (!revisit.isEmpty()) {
            NodeS oldDomS = (NodeS) revisit.poll();
            NodeSet nodes = oldDomS.revisit;
            oldDomS.revisit = null;
            int i = 0;
            while (i < oldDomS.dominated.size) {
                NodeS xS2 = oldDomS.dominated.nodes[i];
                int j3 = 0;
                while (true) {
                    if (j3 >= nodes.size) {
                        id = id2;
                        break;
                    }
                    NodeS nodeS = nodes.nodes[j3];
                    if (isReachableAscending(nodeS, xS2)) {
                        if (xS2.domS == xS2.oldDomS) {
                            if (xS2.oldDomS.revisit == null) {
                                id = id2;
                                xS2.oldDomS.revisit = new NodeSet();
                                revisit.add(xS2.oldDomS);
                            } else {
                                id = id2;
                            }
                            xS2.oldDomS.revisit.add(xS2);
                        } else {
                            id = id2;
                        }
                        oldDomS.dominated.remove(i);
                        xS2.domS = nodeS.domS;
                        xS2.domS.dominated.add(xS2);
                        i--;
                    } else {
                        j3++;
                    }
                }
                i++;
                id2 = id;
            }
            long id4 = id2;
            for (int i2 = 0; i2 < nodes.size; i2++) {
                NodeS nodeS2 = nodes.nodes[i2];
                nodeS2.oldDomS = oldDomS.oldDomS;
                if (nodeS2.oldDomS != nodeS2.domS) {
                    if (nodeS2.oldDomS.revisit == null) {
                        nodeS2.oldDomS.revisit = new NodeSet();
                        revisit.add(nodeS2.oldDomS);
                    }
                    nodeS2.oldDomS.revisit.add(nodeS2);
                }
            }
            this.progress.advance((oldDomS.depth - oldDomS.oldDomS.depth) * ((long) nodes.size));
            id2 = id4;
        }
        this.progress.done();
        revisit.add(rootS);
        while (!revisit.isEmpty()) {
            NodeS nodeS3 = (NodeS) revisit.poll();
            this.graph.setDominatorsComputationState(nodeS3.node, null);
            for (int i3 = 0; i3 < nodeS3.dominated.size; i3++) {
                NodeS xS3 = nodeS3.dominated.nodes[i3];
                this.graph.setDominator(xS3.node, nodeS3.node);
                revisit.add(xS3);
            }
        }
    }

    private static boolean isReachableAscending(NodeS srcS, NodeS dstS) {
        if (dstS.f39id < srcS.f39id) {
            return dstS.inRefIds.hasIdInRange(srcS.f39id, srcS.maxReachableId);
        }
        return dstS.f39id <= srcS.maxReachableId;
    }
}
