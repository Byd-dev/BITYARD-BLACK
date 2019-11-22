package com.ltqh.qh.entity;

import java.util.List;

public class BtcMarketEntity {


    /**
     * data : [{"exchangeId":"acx","rank":"1","baseSymbol":"BTC","baseId":"bitcoin","quoteSymbol":"AUD","quoteId":"australian-dollar","priceQuote":"11213.6100000000000000","priceUsd":"7611.4056951096337789","volumeUsd24Hr":"920917.6755815657882531","percentExchangeVolume":"77.5892335567235968","tradesCount24Hr":"826","updated":1574393850101},{"exchangeId":"acx","rank":"2","baseSymbol":"ETH","baseId":"ethereum","quoteSymbol":"AUD","quoteId":"australian-dollar","priceQuote":"239.1700000000000000","priceUsd":"162.3402187252250712","volumeUsd24Hr":"112464.4982623616626805","percentExchangeVolume":"9.4753683786203409","tradesCount24Hr":"449","updated":1574393850125},{"exchangeId":"acx","rank":"3","baseSymbol":"HC","baseId":"hypercash","quoteSymbol":"AUD","quoteId":"australian-dollar","priceQuote":"2.1100000000000000","priceUsd":"1.4321940942017180","volumeUsd24Hr":"95273.9396754417359939","percentExchangeVolume":"8.0270279888794606","tradesCount24Hr":null,"updated":1574393850260},{"exchangeId":"acx","rank":"4","baseSymbol":"LTC","baseId":"litecoin","quoteSymbol":"AUD","quoteId":"australian-dollar","priceQuote":"74.4900000000000000","priceUsd":"50.5612028801355335","volumeUsd24Hr":"27731.0046325709432777","percentExchangeVolume":"2.3363949376260626","tradesCount24Hr":"315","updated":1574393850223},{"exchangeId":"acx","rank":"5","baseSymbol":"BCH","baseId":"bitcoin-cash","quoteSymbol":"AUD","quoteId":"australian-dollar","priceQuote":"330.4800000000000000","priceUsd":"224.3182484605610300","volumeUsd24Hr":"17949.6994917407870009","percentExchangeVolume":"1.5122995931836980","tradesCount24Hr":"284","updated":1574393850193},{"exchangeId":"acx","rank":"6","baseSymbol":"USDT","baseId":"tether","quoteSymbol":"AUD","quoteId":"australian-dollar","priceQuote":"1.4903940000000000","priceUsd":"1.0116272439970025","volumeUsd24Hr":"12577.4401293454527995","percentExchangeVolume":"1.0596755449668410","tradesCount24Hr":"305","updated":1574393850319},{"exchangeId":"acx","rank":"7","baseSymbol":"BTC","baseId":"bitcoin","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"6889.0000000000000000","priceUsd":"6928.0773706372153894","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393850480},{"exchangeId":"allcoin","rank":"1","baseSymbol":"IFOOD","baseId":"ifoods-chain","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000027600000000","priceUsd":"0.0004456631374608","volumeUsd24Hr":"63162.0920175506773068","percentExchangeVolume":"90.1487186960204206","tradesCount24Hr":"3278","updated":1574393777045},{"exchangeId":"allcoin","rank":"2","baseSymbol":"QBT","baseId":"qbao","quoteSymbol":"QTUM","quoteId":"qtum","priceQuote":"0.0041400000000000","priceUsd":"0.0074111769454247","volumeUsd24Hr":"6754.9691051459245064","percentExchangeVolume":"9.6410962684849160","tradesCount24Hr":"358","updated":1574393806024},{"exchangeId":"allcoin","rank":"3","baseSymbol":"SIGMA","baseId":"sigmacoin","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000040000000000","priceUsd":"0.0006458886050156","volumeUsd24Hr":"91.6606354921823011","percentExchangeVolume":"0.1308235459045145","tradesCount24Hr":"142","updated":1574393747198},{"exchangeId":"allcoin","rank":"4","baseSymbol":"AIDOC","baseId":"aidoc","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000243000000000","priceUsd":"0.0039237732754697","volumeUsd24Hr":"55.2271873277015838","percentExchangeVolume":"0.0788235477285013","tradesCount24Hr":"7","updated":1574393755865},{"exchangeId":"allcoin","rank":"5","baseSymbol":"PUT","baseId":"profile-utility-token","quoteSymbol":"QTUM","quoteId":"qtum","priceQuote":"0.0005010000000000","priceUsd":"0.0008968598187579","volumeUsd24Hr":"0.3769053388330124","percentExchangeVolume":"0.0005379418616477","tradesCount24Hr":"2","updated":1574393808564},{"exchangeId":"allcoin","rank":"6","baseSymbol":"PGD","baseId":"pgd","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000388800000000","priceUsd":"0.0062780372407515","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393770142},{"exchangeId":"allcoin","rank":"7","baseSymbol":"AT","baseId":"aware","quoteSymbol":"USD","quoteId":"united-states-dollar","priceQuote":"0.0148000000000000","priceUsd":"0.0148000000000000","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393842367},{"exchangeId":"allcoin","rank":"8","baseSymbol":"OC","baseId":"oceanchain","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0000000400000000","priceUsd":"0.0003058835611008","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393735169},{"exchangeId":"allcoin","rank":"9","baseSymbol":"WDNA","baseId":"wdna","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000013700000000","priceUsd":"0.0002212168472178","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393779598},{"exchangeId":"allcoin","rank":"10","baseSymbol":"CNET","baseId":"contractnet","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000199000000000","priceUsd":"0.0032132958099526","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393758041},{"exchangeId":"allcoin","rank":"11","baseSymbol":"CNET","baseId":"contractnet","quoteSymbol":"USD","quoteId":"united-states-dollar","priceQuote":"0.0206000000000000","priceUsd":"0.0206000000000000","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393824298},{"exchangeId":"allcoin","rank":"12","baseSymbol":"ZEC","baseId":"zcash","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0045001900000000","priceUsd":"34.4133535707548246","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393714000},{"exchangeId":"allcoin","rank":"13","baseSymbol":"ANS","baseId":"ans","quoteSymbol":"USD","quoteId":"united-states-dollar","priceQuote":"84.9500000000000000","priceUsd":"84.9500000000000000","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393822241},{"exchangeId":"allcoin","rank":"14","baseSymbol":"OC","baseId":"oceanchain","quoteSymbol":"QTUM","quoteId":"qtum","priceQuote":"0.0000387000000000","priceUsd":"0.0000692783931855","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393811090},{"exchangeId":"allcoin","rank":"15","baseSymbol":"IMOS","baseId":"imos","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000067800000000","priceUsd":"0.0010947811855014","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393774539},{"exchangeId":"allcoin","rank":"16","baseSymbol":"ATN","baseId":"atn","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000210000000000","priceUsd":"0.0033909151763318","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393754057},{"exchangeId":"allcoin","rank":"17","baseSymbol":"ALC","baseId":"allcoin","quoteSymbol":"USD","quoteId":"united-states-dollar","priceQuote":"0.0036199000000000","priceUsd":"0.0036199000000000","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393844912},{"exchangeId":"allcoin","rank":"18","baseSymbol":"ACC","baseId":"acchain","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0000012000000000","priceUsd":"0.0091765068330239","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393720399},{"exchangeId":"allcoin","rank":"19","baseSymbol":"VEVUE","baseId":"vevue","quoteSymbol":"QTUM","quoteId":"qtum","priceQuote":"0.0067999900000000","priceUsd":"0.0121729297384344","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393813311},{"exchangeId":"allcoin","rank":"20","baseSymbol":"TIP","baseId":"tip","quoteSymbol":"USD","quoteId":"united-states-dollar","priceQuote":"0.0300000000000000","priceUsd":"0.0300000000000000","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393839854},{"exchangeId":"allcoin","rank":"21","baseSymbol":"BFDT","baseId":"bfdt","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000005900000000","priceUsd":"0.0000952685692398","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393772307},{"exchangeId":"allcoin","rank":"22","baseSymbol":"GRAM","baseId":"gram","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0000719000000000","priceUsd":"0.5498257010786815","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393740089},{"exchangeId":"allcoin","rank":"23","baseSymbol":"TDT","baseId":"tdt","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000047900000000","priceUsd":"0.0007734516045062","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393782099},{"exchangeId":"allcoin","rank":"24","baseSymbol":"HLD","baseId":"hld","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000012000000000","priceUsd":"0.0001937665815047","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393765230},{"exchangeId":"allcoin","rank":"25","baseSymbol":"ATN","baseId":"atn","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0000027500000000","priceUsd":"0.0210294948256798","volumeUsd24Hr":"0.0000000000000000","percentExchangeVolume":"0.0000000000000000","tradesCount24Hr":null,"updated":1574393732627},{"exchangeId":"bibox","rank":"1","baseSymbol":"BTC","baseId":"bitcoin","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"7609.0147000000000000","priceUsd":"7652.1763036603164850","volumeUsd24Hr":"323119325.8500749603818366","percentExchangeVolume":"37.7322291747276112","tradesCount24Hr":"31583","updated":1574393836932},{"exchangeId":"bibox","rank":"2","baseSymbol":"ETH","baseId":"ethereum","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"160.6397000000000000","priceUsd":"161.5509174620338350","volumeUsd24Hr":"115591213.9645535290001681","percentExchangeVolume":"13.4981532423697228","tradesCount24Hr":"31599","updated":1574393838162},{"exchangeId":"bibox","rank":"3","baseSymbol":"EOS","baseId":"eos","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"2.8021000000000000","priceUsd":"2.8179947162523648","volumeUsd24Hr":"89772272.4719882444949393","percentExchangeVolume":"10.4831487548375170","tradesCount24Hr":"31519","updated":1574393844338},{"exchangeId":"bibox","rank":"4","baseSymbol":"BCH","baseId":"bitcoin-cash","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"224.6899000000000000","priceUsd":"225.9644377414340052","volumeUsd24Hr":"74458892.9441496188758596","percentExchangeVolume":"8.6949302870505180","tradesCount24Hr":"31339","updated":1574393648117},{"exchangeId":"bibox","rank":"5","baseSymbol":"LTC","baseId":"litecoin","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"50.4098000000000000","priceUsd":"50.6957460645010742","volumeUsd24Hr":"35854797.8859644898640428","percentExchangeVolume":"4.1869406829428657","tradesCount24Hr":"29373","updated":1574393841861},{"exchangeId":"bibox","rank":"6","baseSymbol":"ETH","baseId":"ethereum","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0211104900000000","priceUsd":"161.4337964445687889","volumeUsd24Hr":"26084439.4450043201727214","percentExchangeVolume":"3.0460079861947984","tradesCount24Hr":"29846","updated":1574393810895},{"exchangeId":"bibox","rank":"7","baseSymbol":"BSV","baseId":"bitcoin-sv","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"100.1612000000000000","priceUsd":"100.7293574010550527","volumeUsd24Hr":"23425701.9552624319214087","percentExchangeVolume":"2.7355341635150348","tradesCount24Hr":"21928","updated":1574393671714},{"exchangeId":"bibox","rank":"8","baseSymbol":"BCH","baseId":"bitcoin-cash","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0295870100000000","priceUsd":"226.2544995281218580","volumeUsd24Hr":"20943893.9769221520101141","percentExchangeVolume":"2.4457212680466497","tradesCount24Hr":"19000","updated":1574393813397},{"exchangeId":"bibox","rank":"9","baseSymbol":"EOS","baseId":"eos","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0003680200000000","priceUsd":"2.8142817039078773","volumeUsd24Hr":"16951127.1938346815228919","percentExchangeVolume":"1.9794662989130496","tradesCount24Hr":"21328","updated":1574393820854},{"exchangeId":"bibox","rank":"10","baseSymbol":"ETC","baseId":"ethereum-classic","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"4.1834000000000000","priceUsd":"4.2071300438849945","volumeUsd24Hr":"16568085.3503859661336234","percentExchangeVolume":"1.9347366233280517","tradesCount24Hr":"21075","updated":1574393843097},{"exchangeId":"bibox","rank":"11","baseSymbol":"BSV","baseId":"bitcoin-sv","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0131735100000000","priceUsd":"100.7390037749238121","volumeUsd24Hr":"11754206.5003664437092909","percentExchangeVolume":"1.3725963690722920","tradesCount24Hr":"12041","updated":1574393669246},{"exchangeId":"bibox","rank":"12","baseSymbol":"EOS","baseId":"eos","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0174488600000000","priceUsd":"2.8175049611280777","volumeUsd24Hr":"11026489.0316968576919915","percentExchangeVolume":"1.2876172294617012","tradesCount24Hr":"27465","updated":1574393822084},{"exchangeId":"bibox","rank":"13","baseSymbol":"ONT","baseId":"ontology","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"0.6688000000000000","priceUsd":"0.6725937212196501","volumeUsd24Hr":"9884036.9521122883185433","percentExchangeVolume":"1.1542074942977000","tradesCount24Hr":"20912","updated":1574393654351},{"exchangeId":"bibox","rank":"14","baseSymbol":"LTC","baseId":"litecoin","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0066244800000000","priceUsd":"50.6579883210250947","volumeUsd24Hr":"9525622.0055316239172621","percentExchangeVolume":"1.1123536223002562","tradesCount24Hr":"13369","updated":1574393812133},{"exchangeId":"bibox","rank":"15","baseSymbol":"NEO","baseId":"neo","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"10.9018000000000000","priceUsd":"10.9636396979551161","volumeUsd24Hr":"8786791.6311481072852052","percentExchangeVolume":"1.0260767741601864","tradesCount24Hr":"20705","updated":1574393585776},{"exchangeId":"bibox","rank":"16","baseSymbol":"ETC","baseId":"ethereum-classic","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0260535100000000","priceUsd":"4.2069163074149247","volumeUsd24Hr":"5717173.9163682054033582","percentExchangeVolume":"0.6676224514787253","tradesCount24Hr":"26000","updated":1574393815864},{"exchangeId":"bibox","rank":"17","baseSymbol":"LTC","baseId":"litecoin","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.3138543000000000","priceUsd":"50.6787290012860457","volumeUsd24Hr":"5650695.8792981033467607","percentExchangeVolume":"0.6598594848928796","tradesCount24Hr":"26105","updated":1574393818368},{"exchangeId":"bibox","rank":"18","baseSymbol":"NEO","baseId":"neo","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0680047400000000","priceUsd":"10.9808716632619569","volumeUsd24Hr":"5644867.2342331938986269","percentExchangeVolume":"0.6591788454084145","tradesCount24Hr":"25736","updated":1574393584540},{"exchangeId":"bibox","rank":"19","baseSymbol":"ONT","baseId":"ontology","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0000880200000000","priceUsd":"0.6730967762023025","volumeUsd24Hr":"5620880.0441461641968330","percentExchangeVolume":"0.6563777435206199","tradesCount24Hr":"16469","updated":1574393651884},{"exchangeId":"bibox","rank":"20","baseSymbol":"BSV","baseId":"bitcoin-sv","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.6421465900000000","priceUsd":"103.6887913076543475","volumeUsd24Hr":"5578523.6442446147191847","percentExchangeVolume":"0.6514315788679887","tradesCount24Hr":"31156","updated":1574393670482},{"exchangeId":"bibox","rank":"21","baseSymbol":"BCH","baseId":"bitcoin-cash","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"1.4002987900000000","priceUsd":"226.1092580195293112","volumeUsd24Hr":"5419281.1257995092126373","percentExchangeVolume":"0.6328360486114065","tradesCount24Hr":"25147","updated":1574393817130},{"exchangeId":"bibox","rank":"22","baseSymbol":"DASH","baseId":"dash","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.3810455400000000","priceUsd":"61.5282430695029572","volumeUsd24Hr":"5208700.5571567908033863","percentExchangeVolume":"0.6082455223255902","tradesCount24Hr":"4729","updated":1574393588239},{"exchangeId":"bibox","rank":"23","baseSymbol":"NEO","baseId":"neo","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0014329800000000","priceUsd":"10.9581256346554809","volumeUsd24Hr":"4296844.0930541687770631","percentExchangeVolume":"0.5017635686774781","tradesCount24Hr":"10137","updated":1574393878582},{"exchangeId":"bibox","rank":"24","baseSymbol":"ONT","baseId":"ontology","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0041753400000000","priceUsd":"0.6742011320164474","volumeUsd24Hr":"1840963.2865702358319989","percentExchangeVolume":"0.2149783162872731","tradesCount24Hr":"12704","updated":1574393653118},{"exchangeId":"bibox","rank":"25","baseSymbol":"BIX","baseId":"bibox-token","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0007394400000000","priceUsd":"0.1193989675231818","volumeUsd24Hr":"1771319.1994800025010069","percentExchangeVolume":"0.2068456345052720","tradesCount24Hr":"21184","updated":1574393809570},{"exchangeId":"bibox","rank":"26","baseSymbol":"BIX","baseId":"bibox-token","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"0.1189000000000000","priceUsd":"0.1195744519333379","volumeUsd24Hr":"1659192.8918807620291883","percentExchangeVolume":"0.1937520953809252","tradesCount24Hr":"19881","updated":1574393856229},{"exchangeId":"bibox","rank":"27","baseSymbol":"DASH","baseId":"dash","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"60.4856000000000000","priceUsd":"60.8287003352321607","volumeUsd24Hr":"1448557.5890841645420468","percentExchangeVolume":"0.1691551775193886","tradesCount24Hr":"4809","updated":1574393589484},{"exchangeId":"bibox","rank":"28","baseSymbol":"DASH","baseId":"dash","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0079440000000000","priceUsd":"60.7484752346181666","volumeUsd24Hr":"1357071.7790752099879230","percentExchangeVolume":"0.1584719305783030","tradesCount24Hr":"4885","updated":1574393587010},{"exchangeId":"bibox","rank":"29","baseSymbol":"ETC","baseId":"ethereum-classic","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0005499800000000","priceUsd":"4.2057460233554002","volumeUsd24Hr":"1077646.1451778147411862","percentExchangeVolume":"0.1258420282108968","tradesCount24Hr":"2749","updated":1574393814629},{"exchangeId":"bibox","rank":"30","baseSymbol":"ALGO","baseId":"algorand","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0000320900000000","priceUsd":"0.2453950868931139","volumeUsd24Hr":"1036380.2680718282620849","percentExchangeVolume":"0.1210232092561256","tradesCount24Hr":"4730","updated":1574393720389},{"exchangeId":"bibox","rank":"31","baseSymbol":"GRIN","baseId":"grin","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0001460100000000","priceUsd":"1.1165514689081821","volumeUsd24Hr":"976081.5671367473482998","percentExchangeVolume":"0.1139818340717873","tradesCount24Hr":"4858","updated":1574393680397},{"exchangeId":"bibox","rank":"32","baseSymbol":"QTUM","baseId":"qtum","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0002350000000000","priceUsd":"1.7970659214671789","volumeUsd24Hr":"965154.8928712294450530","percentExchangeVolume":"0.1127058727023475","tradesCount24Hr":"4823","updated":1574393874864},{"exchangeId":"bibox","rank":"33","baseSymbol":"TRX","baseId":"tron","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0000020100000000","priceUsd":"0.0153706489453150","volumeUsd24Hr":"884862.8771378661979144","percentExchangeVolume":"0.1033297800449934","tradesCount24Hr":"4524","updated":1574393862428},{"exchangeId":"bibox","rank":"34","baseSymbol":"HT","baseId":"huobi-token","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"3.1090000000000000","priceUsd":"3.1266355850357240","volumeUsd24Hr":"798315.7596877349966809","percentExchangeVolume":"0.0932232484673816","tradesCount24Hr":"2632","updated":1574393641780},{"exchangeId":"bibox","rank":"35","baseSymbol":"ALGO","baseId":"algorand","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"0.2443000000000000","priceUsd":"0.2456857746620223","volumeUsd24Hr":"680590.9079259483831551","percentExchangeVolume":"0.0794759398700072","tradesCount24Hr":"4837","updated":1574393722891},{"exchangeId":"bibox","rank":"36","baseSymbol":"QTUM","baseId":"qtum","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"1.7863000000000000","priceUsd":"1.7964326618042180","volumeUsd24Hr":"623117.5040690559266741","percentExchangeVolume":"0.0727644884887730","tradesCount24Hr":"4900","updated":1574393877344},{"exchangeId":"bibox","rank":"37","baseSymbol":"ALGO","baseId":"algorand","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0015190000000000","priceUsd":"0.2452761977546699","volumeUsd24Hr":"441786.1114074814762840","percentExchangeVolume":"0.0515895320033361","tradesCount24Hr":"4467","updated":1574393721663},{"exchangeId":"bibox","rank":"38","baseSymbol":"QTUM","baseId":"qtum","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0111180000000000","priceUsd":"1.7952473776408297","volumeUsd24Hr":"395314.1367980404268001","percentExchangeVolume":"0.0461627714975929","tradesCount24Hr":"4308","updated":1574393876098},{"exchangeId":"bibox","rank":"39","baseSymbol":"GRIN","baseId":"grin","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0067343800000000","priceUsd":"1.0874148259612206","volumeUsd24Hr":"377207.1294082820462173","percentExchangeVolume":"0.0440483273964813","tradesCount24Hr":"4317","updated":1574393679162},{"exchangeId":"bibox","rank":"40","baseSymbol":"TRX","baseId":"tron","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000954200000000","priceUsd":"0.0154076726726469","volumeUsd24Hr":"373247.7006019555713418","percentExchangeVolume":"0.0435859654664783","tradesCount24Hr":"4349","updated":1574393863672},{"exchangeId":"bibox","rank":"41","baseSymbol":"RDN","baseId":"raiden-network-token","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0008412400000000","priceUsd":"0.1358368325208285","volumeUsd24Hr":"329581.7421418062609268","percentExchangeVolume":"0.0384868772351635","tradesCount24Hr":"3894","updated":1574393830747},{"exchangeId":"bibox","rank":"42","baseSymbol":"HT","baseId":"huobi-token","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0004090300000000","priceUsd":"3.1278888249264689","volumeUsd24Hr":"293489.1587803416364267","percentExchangeVolume":"0.0342721691754709","tradesCount24Hr":"950","updated":1574393639194},{"exchangeId":"bibox","rank":"43","baseSymbol":"BIX","baseId":"bibox-token","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0000156400000000","priceUsd":"0.1196004723904114","volumeUsd24Hr":"288836.8321888039705914","percentExchangeVolume":"0.0337288941711493","tradesCount24Hr":"20779","updated":1574393808342},{"exchangeId":"bibox","rank":"44","baseSymbol":"PI","baseId":"pchain","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000333000000000","priceUsd":"0.0053770226367548","volumeUsd24Hr":"226555.3554392263723291","percentExchangeVolume":"0.0264559805257862","tradesCount24Hr":"2384","updated":1574393616845},{"exchangeId":"bibox","rank":"45","baseSymbol":"CPC","baseId":"cpchain","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000370500000000","priceUsd":"0.0059825432039569","volumeUsd24Hr":"165558.8133444989049406","percentExchangeVolume":"0.0193331150050403","tradesCount24Hr":"1948","updated":1574393861184},{"exchangeId":"bibox","rank":"46","baseSymbol":"CZR","baseId":"cononchain","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000193500000000","priceUsd":"0.0031244861267629","volumeUsd24Hr":"143602.5639182634823351","percentExchangeVolume":"0.0167691760237099","tradesCount24Hr":"2870","updated":1574393858704},{"exchangeId":"bibox","rank":"47","baseSymbol":"CPC","baseId":"cpchain","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0000007800000000","priceUsd":"0.0059647294414655","volumeUsd24Hr":"139417.4093819560142320","percentExchangeVolume":"0.0162804549926167","tradesCount24Hr":"1230","updated":1574393859948},{"exchangeId":"bibox","rank":"48","baseSymbol":"HPB","baseId":"high-performance-blockchain","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0005511100000000","priceUsd":"0.0889889172775353","volumeUsd24Hr":"122516.4902107119010097","percentExchangeVolume":"0.0143068517308643","tradesCount24Hr":"3463","updated":1574393835697},{"exchangeId":"bibox","rank":"49","baseSymbol":"KEY","baseId":"key","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000093000000000","priceUsd":"0.0015016910066612","volumeUsd24Hr":"122234.4390796268024256","percentExchangeVolume":"0.0142739152363074","tradesCount24Hr":"1112","updated":1574393873631},{"exchangeId":"bibox","rank":"50","baseSymbol":"HPB","baseId":"high-performance-blockchain","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"0.0895000000000000","priceUsd":"0.0900076824897708","volumeUsd24Hr":"110438.5949779824935975","percentExchangeVolume":"0.0128964566402248","tradesCount24Hr":"3265","updated":1574393595688},{"exchangeId":"bibox","rank":"51","baseSymbol":"LKN","baseId":"lkn","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000023500000000","priceUsd":"0.0003794595554467","volumeUsd24Hr":"98771.7700195551590824","percentExchangeVolume":"0.0115340642425721","tradesCount24Hr":"18365","updated":1574393601874},{"exchangeId":"bibox","rank":"52","baseSymbol":"HT","baseId":"huobi-token","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0193987400000000","priceUsd":"3.1323562794150269","volumeUsd24Hr":"88129.5440029702301755","percentExchangeVolume":"0.0102913192909026","tradesCount24Hr":"600","updated":1574393640541},{"exchangeId":"bibox","rank":"53","baseSymbol":"HPB","baseId":"high-performance-blockchain","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0000115900000000","priceUsd":"0.0886297618289558","volumeUsd24Hr":"70502.9362084295030202","percentExchangeVolume":"0.0082329738077691","tradesCount24Hr":"1459","updated":1574393834465},{"exchangeId":"bibox","rank":"54","baseSymbol":"ITC","baseId":"iot-chain","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0000155300000000","priceUsd":"0.1187592925973842","volumeUsd24Hr":"63272.5904817995585833","percentExchangeVolume":"0.0073886508591123","tradesCount24Hr":"5645","updated":1574393848046},{"exchangeId":"bibox","rank":"55","baseSymbol":"MT","baseId":"mytoken","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000078000000000","priceUsd":"0.0012594827797804","volumeUsd24Hr":"52813.2484456254802291","percentExchangeVolume":"0.0061672621672180","tradesCount24Hr":null,"updated":1574393598156},{"exchangeId":"bibox","rank":"56","baseSymbol":"ITC","baseId":"iot-chain","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0007396400000000","priceUsd":"0.1194312619534326","volumeUsd24Hr":"52204.1944465531778105","percentExchangeVolume":"0.0060961399432151","tradesCount24Hr":"5826","updated":1574393849275},{"exchangeId":"bibox","rank":"57","baseSymbol":"EOSDAC","baseId":"eosdac","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000158100000000","priceUsd":"0.0025528747113241","volumeUsd24Hr":"45795.3082308659835993","percentExchangeVolume":"0.0053477428524225","tradesCount24Hr":"13733","updated":1574393600621},{"exchangeId":"bibox","rank":"58","baseSymbol":"RED","baseId":"red","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000224600000000","priceUsd":"0.0036266645171625","volumeUsd24Hr":"42136.3054072486169914","percentExchangeVolume":"0.0049204631385630","tradesCount24Hr":"24147","updated":1574393609314},{"exchangeId":"bibox","rank":"59","baseSymbol":"CAR","baseId":"carblock","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000042600000000","priceUsd":"0.0006878713643416","volumeUsd24Hr":"32120.4310269063227359","percentExchangeVolume":"0.0037508603408656","tradesCount24Hr":"10305","updated":1574393633012},{"exchangeId":"bibox","rank":"60","baseSymbol":"EOSDAC","baseId":"eosdac","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0000003400000000","priceUsd":"0.0026000102693568","volumeUsd24Hr":"28592.4572095462517105","percentExchangeVolume":"0.0033388815270052","tradesCount24Hr":"11962","updated":1574393599388},{"exchangeId":"bibox","rank":"61","baseSymbol":"XNK","baseId":"ink-protocol","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000046400000000","priceUsd":"0.0007492307818181","volumeUsd24Hr":"27456.7176189756916055","percentExchangeVolume":"0.0032062556421205","tradesCount24Hr":"1645","updated":1574393646874},{"exchangeId":"bibox","rank":"62","baseSymbol":"INSTAR","baseId":"insights-network","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0000017000000000","priceUsd":"0.0130000513467838","volumeUsd24Hr":"25854.7244635619201432","percentExchangeVolume":"0.0030191830406368","tradesCount24Hr":"47","updated":1574393604344},{"exchangeId":"bibox","rank":"63","baseSymbol":"GNX","baseId":"genaro-network","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000546100000000","priceUsd":"0.0088179941799753","volumeUsd24Hr":"22537.4293797724355610","percentExchangeVolume":"0.0026318062162626","tradesCount24Hr":"14383","updated":1574393850507},{"exchangeId":"bibox","rank":"64","baseSymbol":"XNK","baseId":"ink-protocol","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0000001000000000","priceUsd":"0.0007647089027520","volumeUsd24Hr":"19433.8534585336485009","percentExchangeVolume":"0.0022693864271854","tradesCount24Hr":"1276","updated":1574393645619},{"exchangeId":"bibox","rank":"65","baseSymbol":"TTC","baseId":"ttc-protocol","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0001840200000000","priceUsd":"0.0297141052737422","volumeUsd24Hr":"18954.2415539510683767","percentExchangeVolume":"0.0022133797917079","tradesCount24Hr":"974","updated":1574393631775},{"exchangeId":"bibox","rank":"66","baseSymbol":"FSN","baseId":"fusion","quoteSymbol":"USDT","quoteId":"tether","priceQuote":"0.3017000000000000","priceUsd":"0.3034113721470820","volumeUsd24Hr":"16547.3726814215420001","percentExchangeVolume":"0.0019323179033393","tradesCount24Hr":"11748","updated":1574393606829},{"exchangeId":"bibox","rank":"67","baseSymbol":"BU","baseId":"bumo","quoteSymbol":"ETH","quoteId":"ethereum","priceQuote":"0.0000530500000000","priceUsd":"0.0085660976240192","volumeUsd24Hr":"13804.8650904743296922","percentExchangeVolume":"0.0016120618348952","tradesCount24Hr":"1833","updated":1574393636715},{"exchangeId":"bibox","rank":"68","baseSymbol":"TTC","baseId":"ttc-protocol","quoteSymbol":"BTC","quoteId":"bitcoin","priceQuote":"0.0000040300000000","priceUsd":"0.0308177687809052","volumeUsd24Hr":"13362.9662985112846912","percentExchangeVolume":"0.0015604591446305","tradesCount24Hr":"907","updated":1574393630545}]
     * timestamp : 1574393931395
     */

    private long timestamp;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "BtcMarketEntity{" +
                "timestamp=" + timestamp +
                ", data=" + data +
                '}';
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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
                    "exchangeId='" + exchangeId + '\'' +
                    ", rank='" + rank + '\'' +
                    ", baseSymbol='" + baseSymbol + '\'' +
                    ", baseId='" + baseId + '\'' +
                    ", quoteSymbol='" + quoteSymbol + '\'' +
                    ", quoteId='" + quoteId + '\'' +
                    ", priceQuote='" + priceQuote + '\'' +
                    ", priceUsd='" + priceUsd + '\'' +
                    ", volumeUsd24Hr='" + volumeUsd24Hr + '\'' +
                    ", percentExchangeVolume='" + percentExchangeVolume + '\'' +
                    ", tradesCount24Hr='" + tradesCount24Hr + '\'' +
                    ", updated=" + updated +
                    '}';
        }

        /**
         * exchangeId : acx
         * rank : 1
         * baseSymbol : BTC
         * baseId : bitcoin
         * quoteSymbol : AUD
         * quoteId : australian-dollar
         * priceQuote : 11213.6100000000000000
         * priceUsd : 7611.4056951096337789
         * volumeUsd24Hr : 920917.6755815657882531
         * percentExchangeVolume : 77.5892335567235968
         * tradesCount24Hr : 826
         * updated : 1574393850101
         */



        private String exchangeId;
        private String rank;
        private String baseSymbol;
        private String baseId;
        private String quoteSymbol;
        private String quoteId;
        private String priceQuote;
        private String priceUsd;
        private String volumeUsd24Hr;
        private String percentExchangeVolume;
        private String tradesCount24Hr;
        private long updated;

        public String getExchangeId() {
            return exchangeId;
        }

        public void setExchangeId(String exchangeId) {
            this.exchangeId = exchangeId;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getBaseSymbol() {
            return baseSymbol;
        }

        public void setBaseSymbol(String baseSymbol) {
            this.baseSymbol = baseSymbol;
        }

        public String getBaseId() {
            return baseId;
        }

        public void setBaseId(String baseId) {
            this.baseId = baseId;
        }

        public String getQuoteSymbol() {
            return quoteSymbol;
        }

        public void setQuoteSymbol(String quoteSymbol) {
            this.quoteSymbol = quoteSymbol;
        }

        public String getQuoteId() {
            return quoteId;
        }

        public void setQuoteId(String quoteId) {
            this.quoteId = quoteId;
        }

        public String getPriceQuote() {
            return priceQuote;
        }

        public void setPriceQuote(String priceQuote) {
            this.priceQuote = priceQuote;
        }

        public String getPriceUsd() {
            return priceUsd;
        }

        public void setPriceUsd(String priceUsd) {
            this.priceUsd = priceUsd;
        }

        public String getVolumeUsd24Hr() {
            return volumeUsd24Hr;
        }

        public void setVolumeUsd24Hr(String volumeUsd24Hr) {
            this.volumeUsd24Hr = volumeUsd24Hr;
        }

        public String getPercentExchangeVolume() {
            return percentExchangeVolume;
        }

        public void setPercentExchangeVolume(String percentExchangeVolume) {
            this.percentExchangeVolume = percentExchangeVolume;
        }

        public String getTradesCount24Hr() {
            return tradesCount24Hr;
        }

        public void setTradesCount24Hr(String tradesCount24Hr) {
            this.tradesCount24Hr = tradesCount24Hr;
        }

        public long getUpdated() {
            return updated;
        }

        public void setUpdated(long updated) {
            this.updated = updated;
        }
    }
}
