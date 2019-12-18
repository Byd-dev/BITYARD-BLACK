package com.ltqh.qh.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BtcPriceEntity {


    /**
     * code : 1
     * msg : success
     * data : [{"symbol":"BTC","name":"Bitcoin","coincode":"Yml0Y29pbg==","cn_name":"比特币","img":"https://static.hqz.com/coin/bitcoin.png","market_price":"¥9,195.59亿","cny_price":"¥50,855.42","market_number":"1,808万*","24_turnover":"¥9,818,128.59万","24_change":"-0.62%"},{"symbol":"ETH","name":"Ethereum","coincode":"ZXRoZXJldW0=","cn_name":"以太坊","img":"https://static.hqz.com/coin/ethereum.png","market_price":"¥1,141.57亿","cny_price":"¥1,049.45","market_number":"10,878万*","24_turnover":"¥4,518,058.97万","24_change":"-0.96%"},{"symbol":"XRP","name":"Ripple","coincode":"cmlwcGxl","cn_name":"瑞波币","img":"https://static.hqz.com/coin/ripple.png","market_price":"¥654.99亿","cny_price":"¥1.51","market_number":"4,329,989万*","24_turnover":"¥715,011.08万","24_change":"-1.22%"},{"symbol":"USDT","name":"Tether","coincode":"dGV0aGVy","cn_name":"泰达币","img":"https://static.hqz.com/coin/tether.png","market_price":"¥290.29亿","cny_price":"¥7.07","market_number":"410,804万*","24_turnover":"¥12,335,644.57万","24_change":"1.22%"},{"symbol":"BCH","name":"Bitcoin-cash","coincode":"Yml0Y29pbi1jYXNo","cn_name":"比特币现金","img":"https://static.hqz.com/coin/bitcoin-cash.png","market_price":"¥270.08亿","cny_price":"¥1,488.37","market_number":"1,815万*","24_turnover":"¥746,808.53万","24_change":"0.59%"},{"symbol":"LTC","name":"Litecoin","coincode":"bGl0ZWNvaW4=","cn_name":"莱特币","img":"https://static.hqz.com/coin/litecoin.png","market_price":"¥202.87亿","cny_price":"¥318.18","market_number":"6,376万*","24_turnover":"¥1,401,128.07万","24_change":"-1.87%"},{"symbol":"EOS","name":"Eos","coincode":"ZW9z","cn_name":"柚子","img":"https://static.hqz.com/coin/eos.png","market_price":"¥175.27亿","cny_price":"¥18.59","market_number":"94,285万*","24_turnover":"¥1,367,513.51万","24_change":"-1.19%"},{"symbol":"BNB","name":"Binance-coin","coincode":"YmluYW5jZS1jb2lu","cn_name":"币安币","img":"https://static.hqz.com/coin/binance-coin.png","market_price":"¥166.60亿","cny_price":"¥107.11","market_number":"15,554万*","24_turnover":"¥89,377.55万","24_change":"1.16%"},{"symbol":"XLM","name":"Stellar","coincode":"c3RlbGxhcg==","cn_name":"恒星币","img":"https://static.hqz.com/coin/stellar.png","market_price":"¥77.65亿","cny_price":"¥0.3872","market_number":"2,005,478万*","24_turnover":"¥89,568.09万","24_change":"-2.55%"},{"symbol":"ADA","name":"Cardano","coincode":"Y2FyZGFubw==","cn_name":"艾达币","img":"https://static.hqz.com/coin/cardano.png","market_price":"¥68.19亿","cny_price":"¥0.2630","market_number":"2,592,707万*","24_turnover":"¥20,652.62万","24_change":"-1.29%"},{"symbol":"TRX","name":"Tron","coincode":"dHJvbg==","cn_name":"波场","img":"https://static.hqz.com/coin/tron.png","market_price":"¥67.96亿","cny_price":"¥0.1019","market_number":"6,668,207万*","24_turnover":"¥275,228.87万","24_change":"-4.6%"},{"symbol":"XMR","name":"Monero","coincode":"bW9uZXJv","cn_name":"门罗币","img":"https://static.hqz.com/coin/monero.png","market_price":"¥66.82亿","cny_price":"¥385.41","market_number":"1,734万*","24_turnover":"¥95,037.56万","24_change":"3.15%"},{"symbol":"XTZ","name":"Tezos","coincode":"dGV6b3M=","cn_name":"","img":"https://static.hqz.com/coin/tezos.png","market_price":"¥60.08亿","cny_price":"¥9.10","market_number":"66,037万*","24_turnover":"¥11,653.35万","24_change":"7%"},{"symbol":"LINK","name":"Chainlink","coincode":"Y2hhaW5saW5r","cn_name":"","img":"https://static.hqz.com/coin/chainlink.png","market_price":"¥50.95亿","cny_price":"¥14.56","market_number":"35,000万*","24_turnover":"¥63,228.21万","24_change":"-1.22%"},{"symbol":"HT","name":"Huobi-token","coincode":"aHVvYmktdG9rZW4=","cn_name":"火币积分","img":"https://static.hqz.com/coin/huobi-token.png","market_price":"¥48.30亿","cny_price":"¥20.02","market_number":"24,128万*","24_turnover":"¥86,794.45万","24_change":"-1.02%"},{"symbol":"NEO","name":"Neo","coincode":"bmVv","cn_name":"小蚁","img":"https://static.hqz.com/coin/neo.png","market_price":"¥43.47亿","cny_price":"¥61.63","market_number":"7,054万*","24_turnover":"¥212,248.54万","24_change":"-1.33%"},{"symbol":"MKR","name":"Maker","coincode":"bWFrZXI=","cn_name":"","img":"https://static.hqz.com/coin/maker.png","market_price":"¥38.87亿","cny_price":"¥3,886.98","market_number":"100万*","24_turnover":"¥1,834.67万","24_change":"-2.19%"},{"symbol":"MIOTA","name":"Iota","coincode":"aW90YQ==","cn_name":"埃欧塔","img":"https://static.hqz.com/coin/iota.png","market_price":"¥38.85亿","cny_price":"¥1.40","market_number":"277,953万*","24_turnover":"¥4,719.98万","24_change":"-1.76%"},{"symbol":"DASH","name":"Dash","coincode":"ZGFzaA==","cn_name":"达世币","img":"https://static.hqz.com/coin/dash.png","market_price":"¥32.73亿","cny_price":"¥356.07","market_number":"919万*","24_turnover":"¥83,051.82万","24_change":"-0.76%"},{"symbol":"ETC","name":"Ethereum-classic","coincode":"ZXRoZXJldW0tY2xhc3NpYw==","cn_name":"以太经典","img":"https://static.hqz.com/coin/ethereum-classic.png","market_price":"¥31.36亿","cny_price":"¥27.13","market_number":"11,556万*","24_turnover":"¥321,299.64万","24_change":"0.26%"},{"symbol":"NUSD","name":"Nusd","coincode":"bnVzZA==","cn_name":"","img":"https://static.hqz.com/coin/nusd.png","market_price":"¥27.31亿","cny_price":"¥2,197.98","market_number":"124万*","24_turnover":"¥32,732.93万","24_change":"21.09%"},{"symbol":"ONT","name":"Ontology","coincode":"b250b2xvZ3k=","cn_name":"本体","img":"https://static.hqz.com/coin/ontology.png","market_price":"¥27.03亿","cny_price":"¥4.24","market_number":"63,735万*","24_turnover":"¥44,765.49万","24_change":"-1.44%"},{"symbol":"XEM","name":"Nem","coincode":"bmVt","cn_name":"新经币","img":"https://static.hqz.com/coin/nem.png","market_price":"¥22.26亿","cny_price":"¥0.2473","market_number":"900,000万*","24_turnover":"¥19,681.23万","24_change":"-1.35%"},{"symbol":"DOGE","name":"Dogecoin","coincode":"ZG9nZWNvaW4=","cn_name":"狗狗币","img":"https://static.hqz.com/coin/dogecoin.png","market_price":"¥18.62亿","cny_price":"¥0.0152","market_number":"12,231,330万*","24_turnover":"¥17,139.92万","24_change":"0.07%"},{"symbol":"BAT","name":"Basic-attention-token","coincode":"YmFzaWMtYXR0ZW50aW9uLXRva2Vu","cn_name":"注意力币","img":"https://static.hqz.com/coin/basic-attention-token.png","market_price":"¥17.92亿","cny_price":"¥1.32","market_number":"135,645万*","24_turnover":"¥18,544.17万","24_change":"-2.33%"},{"symbol":"DCR","name":"Decred","coincode":"ZGVjcmVk","cn_name":"","img":"https://static.hqz.com/coin/decred.png","market_price":"¥17.62亿","cny_price":"¥163.80","market_number":"1,076万*","24_turnover":"¥9,095.93万","24_change":"22.31%"},{"symbol":"ZEC","name":"Zcash","coincode":"emNhc2g=","cn_name":"大零币","img":"https://static.hqz.com/coin/zcash.png","market_price":"¥15.35亿","cny_price":"¥192.07","market_number":"799万*","24_turnover":"¥38,467.63万","24_change":"0.06%"},{"symbol":"QTUM","name":"Qtum","coincode":"cXR1bQ==","cn_name":"量子链","img":"https://static.hqz.com/coin/qtum.png","market_price":"¥11.60亿","cny_price":"¥12.06","market_number":"9,621万*","24_turnover":"¥85,992.25万","24_change":"-0.92%"},{"symbol":"PZM","name":"Prizm","coincode":"cHJpem0=","cn_name":"","img":"https://static.hqz.com/coin/prizm.png","market_price":"¥10.55亿","cny_price":"¥2.74","market_number":"38,510万*","24_turnover":"¥103.34万","24_change":"2.3%"},{"symbol":"ZRX","name":"0x","coincode":"MHg=","cn_name":"0x协议","img":"https://static.hqz.com/coin/0x.png","market_price":"¥10.11亿","cny_price":"¥1.68","market_number":"60,216万*","24_turnover":"¥9,301.58万","24_change":"-3.12%"},{"symbol":"CENNZ","name":"Centrality","coincode":"Y2VudHJhbGl0eQ==","cn_name":"","img":"https://static.hqz.com/coin/centrality.png","market_price":"¥9.89亿","cny_price":"¥0.9247","market_number":"106,918万*","24_turnover":"¥287.11万","24_change":"-4.19%"},{"symbol":"RVN","name":"Ravencoin","coincode":"cmF2ZW5jb2lu","cn_name":"","img":"https://static.hqz.com/coin/ravencoin.png","market_price":"¥9.24亿","cny_price":"¥0.1853","market_number":"498,486万*","24_turnover":"¥25,443.05万","24_change":"20.38%"},{"symbol":"REP","name":"Augur","coincode":"YXVndXI=","cn_name":"","img":"https://static.hqz.com/coin/augur.png","market_price":"¥7.77亿","cny_price":"¥70.67","market_number":"1,100万*","24_turnover":"¥2,493.88万","24_change":"-5.35%"},{"symbol":"SEELE","name":"Seele","coincode":"c2VlbGU=","cn_name":"","img":"https://static.hqz.com/coin/seele.png","market_price":"¥7.63亿","cny_price":"¥1.09","market_number":"69,956万*","24_turnover":"¥43,933.22万","24_change":"0%"},{"symbol":"CNX","name":"Cryptonex","coincode":"Y3J5cHRvbmV4","cn_name":"","img":"https://static.hqz.com/coin/cryptonex.png","market_price":"¥7.40亿","cny_price":"¥13.28","market_number":"5,569万*","24_turnover":"¥2,634.29万","24_change":"1.26%"},{"symbol":"BTG","name":"Bitcoin-gold","coincode":"Yml0Y29pbi1nb2xk","cn_name":"比特币黄金","img":"https://static.hqz.com/coin/bitcoin-gold.png","market_price":"¥7.38亿","cny_price":"¥42.12","market_number":"1,751万*","24_turnover":"¥3,142.34万","24_change":"0.26%"},{"symbol":"OMG","name":"Omisego","coincode":"b21pc2Vnbw==","cn_name":"嫩模币","img":"https://static.hqz.com/coin/omisego.png","market_price":"¥7.25亿","cny_price":"¥5.17","market_number":"14,025万*","24_turnover":"¥25,287.98万","24_change":"-0.21%"},{"symbol":"DAI","name":"Dai","coincode":"ZGFp","cn_name":"","img":"https://static.hqz.com/coin/dai.png","market_price":"¥7.16亿","cny_price":"¥7.04","market_number":"10,169万*","24_turnover":"¥144,656.86万","24_change":"-0.62%"},{"symbol":"KBC","name":"Karatgold-coin","coincode":"a2FyYXRnb2xkLWNvaW4=","cn_name":"","img":"https://static.hqz.com/coin/karatgold-coin.jpg","market_price":"¥7.05亿","cny_price":"¥0.1879","market_number":"375,251万*","24_turnover":"¥1,354.09万","24_change":"1.27%"},{"symbol":"XIN","name":"Mixin","coincode":"bWl4aW4=","cn_name":"","img":"https://static.hqz.com/coin/mixin.png","market_price":"¥6.99亿","cny_price":"¥1,503.75","market_number":"47万*","24_turnover":"¥700.85万","24_change":"-0.83%"},{"symbol":"LSK","name":"Lisk","coincode":"bGlzaw==","cn_name":"应用链","img":"https://static.hqz.com/coin/lisk.png","market_price":"¥6.63亿","cny_price":"¥5.44","market_number":"12,181万*","24_turnover":"¥1,055.49万","24_change":"-4.9%"},{"symbol":"HAV","name":"Havven","coincode":"aGF2dmVu","cn_name":"","img":"https://static.hqz.com/coin/havven.png","market_price":"¥6.01亿","cny_price":"¥8.96","market_number":"6,706万*","24_turnover":"¥55.14万","24_change":"-6.86%"},{"symbol":"KMD","name":"Komodo","coincode":"a29tb2Rv","cn_name":"科莫多币","img":"https://static.hqz.com/coin/komodo.png","market_price":"¥5.59亿","cny_price":"¥4.76","market_number":"11,728万*","24_turnover":"¥1,810.85万","24_change":"-3.92%"},{"symbol":"KCS","name":"Kucoin-shares","coincode":"a3Vjb2luLXNoYXJlcw==","cn_name":"","img":"https://static.hqz.com/coin/kucoin-shares.png","market_price":"¥5.55亿","cny_price":"¥6.74","market_number":"8,236万*","24_turnover":"¥3,577.88万","24_change":"0.53%"},{"symbol":"EKT","name":"Educare","coincode":"ZWR1Y2FyZQ==","cn_name":"EKT通用积分","img":"https://static.hqz.com/coin/educare.png","market_price":"¥5.46亿","cny_price":"¥0.5460","market_number":"100,000万*","24_turnover":"¥3,989.29万","24_change":"-6.9%"},{"symbol":"BTM","name":"Bytom","coincode":"Ynl0b20=","cn_name":"比原链","img":"https://static.hqz.com/coin/bytom.png","market_price":"¥5.45亿","cny_price":"¥0.5440","market_number":"100,250万*","24_turnover":"¥8,368.18万","24_change":"-0.98%"},{"symbol":"BCD","name":"Bitcoin-diamond","coincode":"Yml0Y29pbi1kaWFtb25k","cn_name":"比特币钻石","img":"https://static.hqz.com/coin/bitcoin-diamond.png","market_price":"¥4.90亿","cny_price":"¥2.63","market_number":"18,649万*","24_turnover":"¥1,049.61万","24_change":"0.51%"},{"symbol":"IOST","name":"Iostoken","coincode":"aW9zdG9rZW4=","cn_name":"","img":"https://static.hqz.com/coin/iostoken.png","market_price":"¥4.83亿","cny_price":"¥0.0402","market_number":"1,201,397万*","24_turnover":"¥336.53万","24_change":"-3.66%"},{"symbol":"DGB","name":"Digibyte","coincode":"ZGlnaWJ5dGU=","cn_name":"极特币","img":"https://static.hqz.com/coin/digibyte.png","market_price":"¥4.65亿","cny_price":"¥0.0370","market_number":"1,256,055万*","24_turnover":"¥2,036.75万","24_change":"8.03%"},{"symbol":"ICX","name":"Icon","coincode":"aWNvbg==","cn_name":"","img":"https://static.hqz.com/coin/icon.png","market_price":"¥4.60亿","cny_price":"¥0.9055","market_number":"50,769万*","24_turnover":"¥4,215.20万","24_change":"-0.83%"},{"symbol":"SC","name":"Siacoin","coincode":"c2lhY29pbg==","cn_name":"云存储","img":"https://static.hqz.com/coin/siacoin.png","market_price":"¥4.40亿","cny_price":"¥0.0105","market_number":"4,181,705万*","24_turnover":"¥5,735.84万","24_change":"-0.29%"},{"symbol":"MOF","name":"Molecular-future","coincode":"bW9sZWN1bGFyLWZ1dHVyZQ==","cn_name":"分子未来","img":"https://static.hqz.com/coin/molecular-future.png","market_price":"¥4.39亿","cny_price":"¥10.02","market_number":"4,384万*","24_turnover":"¥23,277.64万","24_change":"-1.69%"},{"symbol":"XVG","name":"Verge","coincode":"dmVyZ2U=","cn_name":"","img":"https://static.hqz.com/coin/verge.png","market_price":"¥4.37亿","cny_price":"¥0.0272","market_number":"1,608,641万*","24_turnover":"¥1,167.35万","24_change":"0.97%"},{"symbol":"MCO","name":"Monaco","coincode":"bW9uYWNv","cn_name":"","img":"https://static.hqz.com/coin/monaco.png","market_price":"¥4.31亿","cny_price":"¥27.29","market_number":"1,579万*","24_turnover":"¥645.81万","24_change":"-1.91%"},{"symbol":"WAVES","name":"Waves","coincode":"d2F2ZXM=","cn_name":"波币","img":"https://static.hqz.com/coin/waves.png","market_price":"¥4.15亿","cny_price":"¥4.12","market_number":"10,050万*","24_turnover":"¥5,449.21万","24_change":"-1.33%"},{"symbol":"MONA","name":"Monacoin","coincode":"bW9uYWNvaW4=","cn_name":"萌奈币","img":"https://static.hqz.com/coin/monacoin.png","market_price":"¥4.02亿","cny_price":"¥6.12","market_number":"6,573万*","24_turnover":"¥663.94万","24_change":"-2.43%"},{"symbol":"BTS","name":"Bitshares","coincode":"Yml0c2hhcmVz","cn_name":"比特股","img":"https://static.hqz.com/coin/bitshares.png","market_price":"¥3.98亿","cny_price":"¥0.1448","market_number":"274,884万*","24_turnover":"¥6,206.65万","24_change":"0.57%"},{"symbol":"ZIL","name":"Zilliqa","coincode":"emlsbGlxYQ==","cn_name":"","img":"https://static.hqz.com/coin/zilliqa.png","market_price":"¥3.85亿","cny_price":"¥0.0396","market_number":"972,801万*","24_turnover":"¥6,627.10万","24_change":"9.17%"},{"symbol":"MAID","name":"Maidsafecoin","coincode":"bWFpZHNhZmVjb2lu","cn_name":"互联网币","img":"https://static.hqz.com/coin/maidsafecoin.png","market_price":"¥3.71亿","cny_price":"¥0.8188","market_number":"45,255万*","24_turnover":"¥192.99万","24_change":"-5.36%"},{"symbol":"HSR","name":"Hshare","coincode":"aHNoYXJl","cn_name":"超级现金","img":"https://static.hqz.com/coin/hshare.png","market_price":"¥3.69亿","cny_price":"¥8.50","market_number":"4,341万*","24_turnover":"¥11,321.54万","24_change":"0.11%"},{"symbol":"NEXO","name":"Nexo","coincode":"bmV4bw==","cn_name":"","img":"https://static.hqz.com/coin/nexo.png","market_price":"¥3.68亿","cny_price":"¥0.6567","market_number":"56,000万*","24_turnover":"¥5,462.51万","24_change":"2.51%"},{"symbol":"THETA","name":"Theta-token","coincode":"dGhldGEtdG9rZW4=","cn_name":"","img":"https://static.hqz.com/coin/theta-token.png","market_price":"¥3.56亿","cny_price":"¥0.5041","market_number":"70,650万*","24_turnover":"¥330.34万","24_change":"0.49%"},{"symbol":"ENJ","name":"Enjin-coin","coincode":"ZW5qaW4tY29pbg==","cn_name":"","img":"https://static.hqz.com/coin/enjin-coin.png","market_price":"¥3.38亿","cny_price":"¥0.4309","market_number":"78,476万*","24_turnover":"¥872.80万","24_change":"4.75%"},{"symbol":"ARDR","name":"Ardor","coincode":"YXJkb3I=","cn_name":"阿朵","img":"https://static.hqz.com/coin/ardor.png","market_price":"¥3.37亿","cny_price":"¥0.3372","market_number":"99,900万*","24_turnover":"¥2,093.96万","24_change":"-4.77%"},{"symbol":"AE","name":"Aeternity","coincode":"YWV0ZXJuaXR5","cn_name":"阿姨币","img":"https://static.hqz.com/coin/aeternity.png","market_price":"¥3.31亿","cny_price":"¥1.13","market_number":"29,308万*","24_turnover":"¥5,434.36万","24_change":"0.32%"},{"symbol":"LA","name":"Latoken","coincode":"bGF0b2tlbg==","cn_name":"","img":"https://static.hqz.com/coin/latoken.png","market_price":"¥3.12亿","cny_price":"¥0.8219","market_number":"38,010万*","24_turnover":"¥666.37万","24_change":"1.34%"},{"symbol":"SNT","name":"Status","coincode":"c3RhdHVz","cn_name":"","img":"https://static.hqz.com/coin/status.png","market_price":"¥2.60亿","cny_price":"¥0.0750","market_number":"347,048万*","24_turnover":"¥610.19万","24_change":"-10.03%"},{"symbol":"DGD","name":"Digixdao","coincode":"ZGlnaXhkYW8=","cn_name":"黄金代币","img":"https://static.hqz.com/coin/digixdao.png","market_price":"¥2.60亿","cny_price":"¥130.09","market_number":"200万*","24_turnover":"¥743.65万","24_change":"5.95%"},{"symbol":"ETN","name":"Electroneum","coincode":"ZWxlY3Ryb25ldW0=","cn_name":"","img":"https://static.hqz.com/coin/electroneum.png","market_price":"¥2.57亿","cny_price":"¥0.0264","market_number":"973,882万*","24_turnover":"¥115.41万","24_change":"-2.34%"},{"symbol":"NPXS","name":"Pundi-x","coincode":"cHVuZGkteA==","cn_name":"","img":"https://static.hqz.com/coin/pundi-x.png","market_price":"¥2.54亿","cny_price":"¥0.0011","market_number":"23,411,200万*","24_turnover":"¥1,048.37万","24_change":"-1.41%"},{"symbol":"ELF","name":"Aelf","coincode":"YWVsZg==","cn_name":"","img":"https://static.hqz.com/606d4500fa2b6436854591c24da6e388.png","market_price":"¥2.44亿","cny_price":"¥0.4479","market_number":"54,448万*","24_turnover":"¥167.90万","24_change":"-8.49%"},{"symbol":"CRPT","name":"Crypterium","coincode":"Y3J5cHRlcml1bQ==","cn_name":"","img":"https://static.hqz.com/coin/crypterium.png","market_price":"¥2.31亿","cny_price":"¥2.87","market_number":"8,032万*","24_turnover":"¥1.44万","24_change":"0%"},{"symbol":"KNC","name":"Kyber-network","coincode":"a3liZXItbmV0d29yaw==","cn_name":"开博网络","img":"https://static.hqz.com/coin/kyber-network.png","market_price":"¥2.19亿","cny_price":"¥1.32","market_number":"16,535万*","24_turnover":"¥3,940.91万","24_change":"6.94%"},{"symbol":"STRAT","name":"Stratis","coincode":"c3RyYXRpcw==","cn_name":"","img":"https://static.hqz.com/coin/stratis.png","market_price":"¥2.16亿","cny_price":"¥2.17","market_number":"9,942万*","24_turnover":"¥700.61万","24_change":"3.48%"},{"symbol":"AOA","name":"Aurora","coincode":"YXVyb3Jh","cn_name":"极光链","img":"https://static.hqz.com/coin/aurora.png","market_price":"¥2.15亿","cny_price":"¥0.0329","market_number":"654,233万*","24_turnover":"¥978.95万","24_change":"8.08%"},{"symbol":"TNT","name":"Tierion","coincode":"dGllcmlvbg==","cn_name":"","img":"https://static.hqz.com/coin/tierion.png","market_price":"¥2.07亿","cny_price":"¥0.4830","market_number":"42,848万*","24_turnover":"¥640.51万","24_change":"-3.26%"},{"symbol":"XZC","name":"Zcoin","coincode":"emNvaW4=","cn_name":"零币","img":"https://static.hqz.com/coin/zcoin.png","market_price":"¥2.06亿","cny_price":"¥24.02","market_number":"857万*","24_turnover":"¥2,712.51万","24_change":"-5.25%"},{"symbol":"VERI","name":"Veritaseum","coincode":"dmVyaXRhc2V1bQ==","cn_name":"","img":"https://static.hqz.com/coin/veritaseum.png","market_price":"¥2.06亿","cny_price":"¥95.60","market_number":"215万*","24_turnover":"¥12.08万","24_change":"-3.69%"},{"symbol":"PPT","name":"Populous","coincode":"cG9wdWxvdXM=","cn_name":"","img":"https://static.hqz.com/coin/populous.png","market_price":"¥1.99亿","cny_price":"¥3.73","market_number":"5,325万*","24_turnover":"¥965.23万","24_change":"-1.58%"},{"symbol":"IGNIS","name":"Ignis","coincode":"aWduaXM=","cn_name":"火链","img":"https://static.hqz.com/coin/ignis.png","market_price":"¥1.96亿","cny_price":"¥0.2574","market_number":"76,114万*","24_turnover":"¥2,988.45万","24_change":"1.71%"},{"symbol":"WICC","name":"Waykichain","coincode":"d2F5a2ljaGFpbg==","cn_name":"维基链","img":"https://static.hqz.com/coin/waykichain.png","market_price":"¥1.91亿","cny_price":"¥1.01","market_number":"18,900万*","24_turnover":"¥3,339.24万","24_change":"16.48%"},{"symbol":"MANA","name":"Decentraland","coincode":"ZGVjZW50cmFsYW5k","cn_name":"","img":"https://static.hqz.com/coin/decentraland.png","market_price":"¥1.89亿","cny_price":"¥0.1802","market_number":"105,014万*","24_turnover":"¥4,105.98万","24_change":"-11.84%"},{"symbol":"FCT","name":"Factom","coincode":"ZmFjdG9t","cn_name":"公证币","img":"https://static.hqz.com/coin/factom.png","market_price":"¥1.81亿","cny_price":"¥18.84","market_number":"963万*","24_turnover":"¥28.69万","24_change":"4.16%"},{"symbol":"DTR","name":"Dynamic-trading-rights","coincode":"ZHluYW1pYy10cmFkaW5nLXJpZ2h0cw==","cn_name":"Tokens","img":"https://static.hqz.com/coin/dynamic-trading-rights.png","market_price":"¥1.81亿","cny_price":"¥0.1189","market_number":"152,127万*","24_turnover":"¥1,789.67","24_change":"-2.44%"},{"symbol":"ELA","name":"Elastos","coincode":"ZWxhc3Rvcw==","cn_name":"亦来云","img":"https://static.hqz.com/coin/elastos.png","market_price":"¥1.71亿","cny_price":"¥10.96","market_number":"1,563万*","24_turnover":"¥1,688.62万","24_change":"-1.61%"},{"symbol":"RCN","name":"Ripio-credit-network","coincode":"cmlwaW8tY3JlZGl0LW5ldHdvcms=","cn_name":"","img":"https://static.hqz.com/coin/ripio-credit-network.png","market_price":"¥1.68亿","cny_price":"¥0.3356","market_number":"50,061万*","24_turnover":"¥1,012.51万","24_change":"-2.74%"},{"symbol":"REN","name":"Republic-protocol","coincode":"cmVwdWJsaWMtcHJvdG9jb2w=","cn_name":"","img":"https://static.hqz.com/coin/republic-protocol.png","market_price":"¥1.67亿","cny_price":"¥0.2659","market_number":"62,716万*","24_turnover":"¥258.25万","24_change":"-0.15%"},{"symbol":"BRD","name":"Bread","coincode":"YnJlYWQ=","cn_name":"","img":"https://static.hqz.com/coin/bread.png","market_price":"¥1.66亿","cny_price":"¥1.87","market_number":"8,873万*","24_turnover":"¥3,318.77万","24_change":"3.19%"},{"symbol":"WAX","name":"Wax","coincode":"d2F4","cn_name":"Worldwide Asset Exch","img":"https://static.hqz.com/9b9673378990ad0fd257852458045fdb.png","market_price":"¥1.61亿","cny_price":"¥0.1709","market_number":"94,282万*","24_turnover":"¥807.66万","24_change":"-1.09%"},{"symbol":"NULS","name":"Nuls","coincode":"bnVscw==","cn_name":"","img":"https://static.hqz.com/coin/nuls.png","market_price":"¥1.59亿","cny_price":"¥2.14","market_number":"7,431万*","24_turnover":"¥2,732.63万","24_change":"-1.29%"},{"symbol":"ICN","name":"Iconomi","coincode":"aWNvbm9taQ==","cn_name":"","img":"https://static.hqz.com/coin/iconomi.png","market_price":"¥1.52亿","cny_price":"¥1.54","market_number":"9,893万*","24_turnover":"¥70.53万","24_change":"0%"},{"symbol":"BTCD","name":"Bitcoindark","coincode":"Yml0Y29pbmRhcms=","cn_name":"","img":"https://static.hqz.com/coin/bitcoindark.png","market_price":"¥1.47亿","cny_price":"¥113.92","market_number":"129万*","24_turnover":"¥144.91万","24_change":"0%"},{"symbol":"ARK","name":"Ark","coincode":"YXJr","cn_name":"","img":"https://static.hqz.com/coin/ark.png","market_price":"¥1.47亿","cny_price":"¥1.30","market_number":"11,296万*","24_turnover":"¥717.48万","24_change":"0.21%"},{"symbol":"AION","name":"Aion","coincode":"YWlvbg==","cn_name":"","img":"https://static.hqz.com/coin/aion.png","market_price":"¥1.45亿","cny_price":"¥0.4551","market_number":"31,844万*","24_turnover":"¥1,359.42万","24_change":"-1.1%"},{"symbol":"WAN","name":"Wanchain","coincode":"d2FuY2hhaW4=","cn_name":"","img":"https://static.hqz.com/coin/wanchain.png","market_price":"¥1.43亿","cny_price":"¥1.34","market_number":"10,615万*","24_turnover":"¥230.98万","24_change":"0.03%"},{"symbol":"R","name":"Revain","coincode":"cmV2YWlu","cn_name":"","img":"https://static.hqz.com/coin/revain.png","market_price":"¥1.39亿","cny_price":"¥0.2860","market_number":"48,445万*","24_turnover":"¥268.06万","24_change":"0.3%"},{"symbol":"RDD","name":"Reddcoin","coincode":"cmVkZGNvaW4=","cn_name":"蜗牛币","img":"https://static.hqz.com/coin/reddcoin.png","market_price":"¥1.35亿","cny_price":"¥0.0047","market_number":"2,880,871万*","24_turnover":"¥37.80万","24_change":"-1.33%"},{"symbol":"XMC","name":"Monero-classic","coincode":"bW9uZXJvLWNsYXNzaWM=","cn_name":"门罗经典","img":"https://static.hqz.com/coin/monero-classic.png","market_price":"¥1.31亿","cny_price":"¥8.25","market_number":"1,583万*","24_turnover":"¥15.15万","24_change":"2.29%"},{"symbol":"TRIG","name":"Triggers","coincode":"dHJpZ2dlcnM=","cn_name":"","img":"https://static.hqz.com/coin/triggers.png","market_price":"¥1.28亿","cny_price":"¥4.00","market_number":"3,211万*","24_turnover":"¥203.03","24_change":"-0.09%"},{"symbol":"QASH","name":"Qash","coincode":"cWFzaA==","cn_name":"","img":"https://static.hqz.com/coin/qash.png","market_price":"¥1.26亿","cny_price":"¥0.3614","market_number":"35,000万*","24_turnover":"¥702.38万","24_change":"1.15%"},{"symbol":"FUN","name":"Funfair","coincode":"ZnVuZmFpcg==","cn_name":"","img":"https://static.hqz.com/coin/funfair.png","market_price":"¥1.21亿","cny_price":"¥0.0217","market_number":"556,859万*","24_turnover":"¥292.73万","24_change":"8.77%"},{"symbol":"PAI","name":"Project-pai","coincode":"cHJvamVjdC1wYWk=","cn_name":"","img":"https://static.hqz.com/53e9ed4a8cb235952744e13fb678b0ce.jpg","market_price":"¥1.20亿","cny_price":"¥0.0826","market_number":"145,008万*","24_turnover":"¥451.23万","24_change":"-5.07%"},{"symbol":"IOTX","name":"Iotex","coincode":"aW90ZXg=","cn_name":"","img":"https://static.hqz.com/coin/iotex.png","market_price":"¥1.20亿","cny_price":"¥0.0899","market_number":"133,104万*","24_turnover":"¥636.15万","24_change":"-0.78%"},{"symbol":"STORJ","name":"Storj","coincode":"c3Rvcmo=","cn_name":"","img":"https://static.hqz.com/coin/storj.png","market_price":"¥1.20亿","cny_price":"¥0.8809","market_number":"13,579万*","24_turnover":"¥1,894.01万","24_change":"-3.93%"},{"symbol":"BNT","name":"Bancor","coincode":"YmFuY29y","cn_name":"","img":"https://static.hqz.com/coin/bancor.png","market_price":"¥1.17亿","cny_price":"¥1.85","market_number":"6,283万*","24_turnover":"¥8,045.18万","24_change":"-2.59%"},{"symbol":"POWR","name":"Power-ledger","coincode":"cG93ZXItbGVkZ2Vy","cn_name":"","img":"https://static.hqz.com/coin/power-ledger.png","market_price":"¥1.16亿","cny_price":"¥0.2853","market_number":"40,485万*","24_turnover":"¥1,290.03万","24_change":"-6.83%"},{"symbol":"C20","name":"C20","coincode":"YzIw","cn_name":"","img":"https://static.hqz.com/coin/c20.png","market_price":"¥1.13亿","cny_price":"¥2.81","market_number":"4,023万*","24_turnover":"¥45.38万","24_change":"-0.28%"},{"symbol":"ANT","name":"Aragon","coincode":"YXJhZ29u","cn_name":"","img":"https://static.hqz.com/coin/aragon.png","market_price":"¥1.13亿","cny_price":"¥3.89","market_number":"2,910万*","24_turnover":"¥6.10万","24_change":"1.69%"},{"symbol":"DATA","name":"Streamr-datacoin","coincode":"c3RyZWFtci1kYXRhY29pbg==","cn_name":"","img":"https://static.hqz.com/coin/streamr-datacoin.png","market_price":"¥1.12亿","cny_price":"¥0.1651","market_number":"67,715万*","24_turnover":"¥1,093.48万","24_change":"0.65%"},{"symbol":"MOAC","name":"Moac","coincode":"bW9hYw==","cn_name":"墨客","img":"https://static.hqz.com/coin/moac.png","market_price":"¥1.12亿","cny_price":"¥1.79","market_number":"6,246万*","24_turnover":"¥3.88万","24_change":"-1.28%"},{"symbol":"DRG","name":"Dragon-coins","coincode":"ZHJhZ29uLWNvaW5z","cn_name":"","img":"https://static.hqz.com/coin/dragon-coins.png","market_price":"¥1.11亿","cny_price":"¥0.3184","market_number":"34,829万*","24_turnover":"¥40.97万","24_change":"-1.16%"},{"symbol":"TOMO","name":"Tomochain","coincode":"dG9tb2NoYWlu","cn_name":"","img":"https://static.hqz.com/coin/tomochain.png","market_price":"¥1.10亿","cny_price":"¥1.99","market_number":"5,500万*","24_turnover":"¥542.90万","24_change":"5.64%"},{"symbol":"LEND","name":"Ethlend","coincode":"ZXRobGVuZA==","cn_name":"","img":"https://static.hqz.com/coin/ethlend.png","market_price":"¥1.07亿","cny_price":"¥0.0970","market_number":"110,018万*","24_turnover":"¥1,675.43万","24_change":"-0.98%"},{"symbol":"GRS","name":"Groestlcoin","coincode":"Z3JvZXN0bGNvaW4=","cn_name":"闪电币","img":"https://static.hqz.com/coin/groestlcoin.png","market_price":"¥1.04亿","cny_price":"¥1.44","market_number":"7,210万*","24_turnover":"¥934.59万","24_change":"-5.4%"},{"symbol":"LOOM","name":"Loom-network","coincode":"bG9vbS1uZXR3b3Jr","cn_name":"","img":"https://static.hqz.com/coin/loom-network.png","market_price":"¥1.03亿","cny_price":"¥0.1300","market_number":"79,406万*","24_turnover":"¥1,407.56万","24_change":"1.14%"},{"symbol":"ODE","name":"Odem","coincode":"b2RlbQ==","cn_name":"","img":"https://static.hqz.com/coin/odem.png","market_price":"¥0.99亿","cny_price":"¥0.4297","market_number":"22,993万*","24_turnover":"¥243.35万","24_change":"-6.04%"},{"symbol":"ABT","name":"Arcblock","coincode":"YXJjYmxvY2s=","cn_name":"区块基石","img":"https://static.hqz.com/coin/arcblock.png","market_price":"¥0.94亿","cny_price":"¥0.9527","market_number":"9,855万*","24_turnover":"¥6,367.51万","24_change":"-1.91%"},{"symbol":"SAFE","name":"Safe","coincode":"c2FmZQ==","cn_name":"安网3","img":"https://static.hqz.com/coin/safe.png","market_price":"¥0.93亿","cny_price":"¥3.22","market_number":"2,900万*","24_turnover":"¥230.54万","24_change":"14.76%"},{"symbol":"LOKI","name":"Loki","coincode":"bG9raQ==","cn_name":"","img":"https://static.hqz.com/coin/loki.png","market_price":"¥0.93亿","cny_price":"¥2.93","market_number":"3,178万*","24_turnover":"¥16.43万","24_change":"-6.03%"},{"symbol":"CTXC","name":"Cortex","coincode":"Y29ydGV4","cn_name":"","img":"https://static.hqz.com/coin/cortex.png","market_price":"¥0.89亿","cny_price":"¥0.5953","market_number":"14,979万*","24_turnover":"¥1,742.68万","24_change":"-8.67%"},{"symbol":"2","name":"Www.hqz.com?type=&sortby=&sort=desc&filter=&p=2","coincode":"d3d3Lmhxei5jb20/dHlwZT0mYW1wO3NvcnRieT0mYW1wO3NvcnQ9ZGVzYyZhbXA7ZmlsdGVyPSZhbXA7cD0y","cn_name":"","img":"","market_price":"","cny_price":"3","market_number":"","24_turnover":"","24_change":""}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "BtcPriceEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "symbol='" + symbol + '\'' +
                    ", name='" + name + '\'' +
                    ", coincode='" + coincode + '\'' +
                    ", cn_name='" + cn_name + '\'' +
                    ", img='" + img + '\'' +
                    ", market_price='" + market_price + '\'' +
                    ", cny_price='" + cny_price + '\'' +
                    ", market_number='" + market_number + '\'' +
                    ", _$24_turnover='" + _$24_turnover + '\'' +
                    ", _$24_change='" + _$24_change + '\'' +
                    '}';
        }

        /**
         * symbol : BTC
         * name : Bitcoin
         * coincode : Yml0Y29pbg==
         * cn_name : 比特币
         * img : https://static.hqz.com/coin/bitcoin.png
         * market_price : ¥9,195.59亿
         * cny_price : ¥50,855.42
         * market_number : 1,808万*
         * 24_turnover : ¥9,818,128.59万
         * 24_change : -0.62%
         */



        private String symbol;
        private String name;
        private String coincode;
        private String cn_name;
        private String img;
        private String market_price;
        private String cny_price;
        private String market_number;
        @SerializedName("24_turnover")
        private String _$24_turnover;
        @SerializedName("24_change")
        private String _$24_change;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCoincode() {
            return coincode;
        }

        public void setCoincode(String coincode) {
            this.coincode = coincode;
        }

        public String getCn_name() {
            return cn_name;
        }

        public void setCn_name(String cn_name) {
            this.cn_name = cn_name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getCny_price() {
            return cny_price;
        }

        public void setCny_price(String cny_price) {
            this.cny_price = cny_price;
        }

        public String getMarket_number() {
            return market_number;
        }

        public void setMarket_number(String market_number) {
            this.market_number = market_number;
        }

        public String get_$24_turnover() {
            return _$24_turnover;
        }

        public void set_$24_turnover(String _$24_turnover) {
            this._$24_turnover = _$24_turnover;
        }

        public String get_$24_change() {
            return _$24_change;
        }

        public void set_$24_change(String _$24_change) {
            this._$24_change = _$24_change;
        }
    }
}
