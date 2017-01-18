/*
 * Copyright by the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import java.io.File;
import java.io.InputStream;


public class SegwitStats {

    public static void main(String[] args) {

        NetworkParameters params = MainNetParams.get();

        // Now we initialize a new WalletAppKit. The kit handles all the boilerplate for us and is the easiest way to get everything up and running.
        // Have a look at the WalletAppKit documentation and its source to understand what's happening behind the scenes: https://github.com/bitcoinj/bitcoinj/blob/master/core/src/main/java/org/bitcoinj/kits/WalletAppKit.java
        WalletAppKit kit = new WalletAppKit(params, new File("."), "snapshot");
        InputStream checkpoint = CheckpointManager.openStream(params);
        kit.setCheckpoints(checkpoint);
        kit.setBlockingStartup(true);
        kit.startAsync();
        kit.awaitRunning();

        int N = Integer.parseInt(args[0]);
        int segwit = 0;
        BlockChain chain = kit.chain();
        StoredBlock block = chain.getChainHead();
        BlockStore store = chain.getBlockStore();
        Block header = block.getHeader();
        try {
            for (int i = 0; i < N; i++) {
                long version = header.getVersion();
                if (version == 536870914) {
                    segwit++;
                }
                Sha256Hash hash = header.getPrevBlockHash();
                block = store.get(hash);
                header = block.getHeader();
            }
        } catch (BlockStoreException e) {
            //System.out.print("error");
        }
        System.out.printf("{\"segwit_blocks\": \""+ segwit +"\", \"blocks\": \""+ N +"\", \"best_chain\": \""+ chain.getBestChainHeight() +"\", \"segwit_rate\": \"%.4f" +"\"}", ((double) segwit/(double) N));
    }

}
