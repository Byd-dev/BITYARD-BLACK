package com.ltqh.qh.entity;

import java.util.List;

public class CoinEntity {


    /**
     * status : {"timestamp":"2019-11-15T09:16:01.198Z","error_code":0,"error_message":null,"elapsed":12,"credit_count":1,"notice":null}
     * data : [{"id":1,"name":"Bitcoin","symbol":"BTC","slug":"bitcoin","num_market_pairs":7767,"date_added":"2013-04-28T00:00:00.000Z","tags":["mineable"],"max_supply":21000000,"circulating_supply":18048562,"total_supply":18048562,"platform":null,"cmc_rank":1,"last_updated":"2019-11-15T09:14:36.000Z","quote":{"USD":{"price":8721.17308305,"volume_24h":1.94308068959716E10,"percent_change_1h":0.985353,"percent_change_24h":-0.050818,"percent_change_7d":-4.21443,"market_cap":1.574046331021591E11,"last_updated":"2019-11-15T09:14:36.000Z"}}},{"id":1027,"name":"Ethereum","symbol":"ETH","slug":"ethereum","num_market_pairs":5385,"date_added":"2015-08-07T00:00:00.000Z","tags":["mineable"],"max_supply":null,"circulating_supply":1.08559740499E8,"total_supply":1.08559740499E8,"platform":null,"cmc_rank":2,"last_updated":"2019-11-15T09:15:23.000Z","quote":{"USD":{"price":185.402724405,"volume_24h":8.13754474849041E9,"percent_change_1h":0.782028,"percent_change_24h":-0.901365,"percent_change_7d":-0.0681692,"market_cap":2.0127271649214413E10,"last_updated":"2019-11-15T09:15:23.000Z"}}},{"id":52,"name":"XRP","symbol":"XRP","slug":"ripple","num_market_pairs":508,"date_added":"2013-08-04T00:00:00.000Z","tags":[],"max_supply":100000000000,"circulating_supply":43298481757,"total_supply":99991298961,"platform":null,"cmc_rank":3,"last_updated":"2019-11-15T09:15:04.000Z","quote":{"USD":{"price":0.269452204339,"volume_24h":1.25962928210864E9,"percent_change_1h":1.0689,"percent_change_24h":0.0772401,"percent_change_7d":-4.50666,"market_cap":1.166687135395563E10,"last_updated":"2019-11-15T09:15:04.000Z"}}},{"id":1831,"name":"Bitcoin Cash","symbol":"BCH","slug":"bitcoin-cash","num_market_pairs":440,"date_added":"2017-07-23T00:00:00.000Z","tags":["mineable"],"max_supply":21000000,"circulating_supply":18113850,"total_supply":18113850,"platform":null,"cmc_rank":4,"last_updated":"2019-11-15T09:15:06.000Z","quote":{"USD":{"price":278.200771359,"volume_24h":1.73356404105338E9,"percent_change_1h":1.09852,"percent_change_24h":-0.464996,"percent_change_7d":-3.43614,"market_cap":5.039287042281222E9,"last_updated":"2019-11-15T09:15:06.000Z"}}},{"id":825,"name":"Tether","symbol":"USDT","slug":"tether","num_market_pairs":3845,"date_added":"2015-02-25T00:00:00.000Z","tags":[],"max_supply":null,"circulating_supply":4.1080444561E9,"total_supply":4.20777150446E9,"platform":{"id":83,"name":"Omni","symbol":"OMNI","slug":"omni","token_address":"31"},"cmc_rank":5,"last_updated":"2019-11-15T09:15:20.000Z","quote":{"USD":{"price":1.00228534509,"volume_24h":2.20029330855269E10,"percent_change_1h":-0.188496,"percent_change_24h":-0.734069,"percent_change_7d":-0.0781311,"market_cap":4.11743275532725E9,"last_updated":"2019-11-15T09:15:20.000Z"}}},{"id":2,"name":"Litecoin","symbol":"LTC","slug":"litecoin","num_market_pairs":559,"date_added":"2013-04-28T00:00:00.000Z","tags":["mineable"],"max_supply":84000000,"circulating_supply":6.36834832674216E7,"total_supply":6.36834832674216E7,"platform":null,"cmc_rank":6,"last_updated":"2019-11-15T09:15:05.000Z","quote":{"USD":{"price":60.4246444281,"volume_24h":2.96559365275806E9,"percent_change_1h":1.25472,"percent_change_24h":1.13282,"percent_change_7d":-1.37994,"market_cap":3.8480518323768067E9,"last_updated":"2019-11-15T09:15:05.000Z"}}},{"id":1839,"name":"Binance Coin","symbol":"BNB","slug":"binance-coin","num_market_pairs":283,"date_added":"2017-07-25T00:00:00.000Z","tags":[],"max_supply":187536713,"circulating_supply":155536713,"total_supply":187536713,"platform":null,"cmc_rank":7,"last_updated":"2019-11-15T09:15:04.000Z","quote":{"USD":{"price":21.0891897341,"volume_24h":2.20747231882003E8,"percent_change_1h":1.01532,"percent_change_24h":0.00271787,"percent_change_7d":4.18358,"market_cap":3.280143251075258E9,"last_updated":"2019-11-15T09:15:04.000Z"}}},{"id":1765,"name":"EOS","symbol":"EOS","slug":"eos","num_market_pairs":394,"date_added":"2017-07-01T00:00:00.000Z","tags":[],"max_supply":null,"circulating_supply":9.402440564948E8,"total_supply":1.0369440679642E9,"platform":null,"cmc_rank":8,"last_updated":"2019-11-15T09:15:05.000Z","quote":{"USD":{"price":3.47592096266,"volume_24h":1.80193032865063E9,"percent_change_1h":0.846805,"percent_change_24h":0.860671,"percent_change_7d":-0.674032,"market_cap":3.2682140259867487E9,"last_updated":"2019-11-15T09:15:05.000Z"}}},{"id":3602,"name":"Bitcoin SV","symbol":"BSV","slug":"bitcoin-sv","num_market_pairs":158,"date_added":"2018-11-09T00:00:00.000Z","tags":["mineable"],"max_supply":21000000,"circulating_supply":18068415,"total_supply":18068415,"platform":null,"cmc_rank":9,"last_updated":"2019-11-15T09:15:12.000Z","quote":{"USD":{"price":127.02533699,"volume_24h":5.43144862269004E8,"percent_change_1h":0.883372,"percent_change_24h":0.192284,"percent_change_7d":-1.6444,"market_cap":2.2951465042501707E9,"last_updated":"2019-11-15T09:15:12.000Z"}}},{"id":512,"name":"Stellar","symbol":"XLM","slug":"stellar","num_market_pairs":308,"date_added":"2014-08-05T00:00:00.000Z","tags":[],"max_supply":null,"circulating_supply":2.00547795537E10,"total_supply":50000000000,"platform":null,"cmc_rank":10,"last_updated":"2019-11-15T09:15:03.000Z","quote":{"USD":{"price":0.0733193479239,"volume_24h":2.08118959569063E8,"percent_change_1h":0.326132,"percent_change_24h":-1.84954,"percent_change_7d":-1.37237,"market_cap":1.4704033596348464E9,"last_updated":"2019-11-15T09:15:03.000Z"}}}]
     */

    private StatusBean status;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "CoinEntity{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class StatusBean {
        /**
         * timestamp : 2019-11-15T09:16:01.198Z
         * error_code : 0
         * error_message : null
         * elapsed : 12
         * credit_count : 1
         * notice : null
         */

        private String timestamp;
        private int error_code;
        private Object error_message;
        private int elapsed;
        private int credit_count;
        private Object notice;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public int getError_code() {
            return error_code;
        }

        public void setError_code(int error_code) {
            this.error_code = error_code;
        }

        public Object getError_message() {
            return error_message;
        }

        public void setError_message(Object error_message) {
            this.error_message = error_message;
        }

        public int getElapsed() {
            return elapsed;
        }

        public void setElapsed(int elapsed) {
            this.elapsed = elapsed;
        }

        public int getCredit_count() {
            return credit_count;
        }

        public void setCredit_count(int credit_count) {
            this.credit_count = credit_count;
        }

        public Object getNotice() {
            return notice;
        }

        public void setNotice(Object notice) {
            this.notice = notice;
        }
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", symbol='" + symbol + '\'' +
                    ", slug='" + slug + '\'' +
                    ", num_market_pairs=" + num_market_pairs +
                    ", date_added='" + date_added + '\'' +
                    ", max_supply=" + max_supply +
                    ", circulating_supply=" + circulating_supply +
                    ", total_supply=" + total_supply +
                    ", platform=" + platform +
                    ", cmc_rank=" + cmc_rank +
                    ", last_updated='" + last_updated + '\'' +
                    ", quote=" + quote +
                    ", tags=" + tags +
                    '}';
        }

        /**
         * id : 1
         * name : Bitcoin
         * symbol : BTC
         * slug : bitcoin
         * num_market_pairs : 7767
         * date_added : 2013-04-28T00:00:00.000Z
         * tags : ["mineable"]
         * max_supply : 21000000
         * circulating_supply : 18048562
         * total_supply : 18048562
         * platform : null
         * cmc_rank : 1
         * last_updated : 2019-11-15T09:14:36.000Z
         * quote : {"USD":{"price":8721.17308305,"volume_24h":1.94308068959716E10,"percent_change_1h":0.985353,"percent_change_24h":-0.050818,"percent_change_7d":-4.21443,"market_cap":1.574046331021591E11,"last_updated":"2019-11-15T09:14:36.000Z"}}
         */



        private int id;
        private String name;
        private String symbol;
        private String slug;
        private int num_market_pairs;
        private String date_added;
        private int max_supply;
        private int circulating_supply;
        private int total_supply;
        private Object platform;
        private int cmc_rank;
        private String last_updated;
        private QuoteBean quote;
        private List<String> tags;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public int getNum_market_pairs() {
            return num_market_pairs;
        }

        public void setNum_market_pairs(int num_market_pairs) {
            this.num_market_pairs = num_market_pairs;
        }

        public String getDate_added() {
            return date_added;
        }

        public void setDate_added(String date_added) {
            this.date_added = date_added;
        }

        public int getMax_supply() {
            return max_supply;
        }

        public void setMax_supply(int max_supply) {
            this.max_supply = max_supply;
        }

        public int getCirculating_supply() {
            return circulating_supply;
        }

        public void setCirculating_supply(int circulating_supply) {
            this.circulating_supply = circulating_supply;
        }

        public int getTotal_supply() {
            return total_supply;
        }

        public void setTotal_supply(int total_supply) {
            this.total_supply = total_supply;
        }

        public Object getPlatform() {
            return platform;
        }

        public void setPlatform(Object platform) {
            this.platform = platform;
        }

        public int getCmc_rank() {
            return cmc_rank;
        }

        public void setCmc_rank(int cmc_rank) {
            this.cmc_rank = cmc_rank;
        }

        public String getLast_updated() {
            return last_updated;
        }

        public void setLast_updated(String last_updated) {
            this.last_updated = last_updated;
        }

        public QuoteBean getQuote() {
            return quote;
        }

        public void setQuote(QuoteBean quote) {
            this.quote = quote;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public static class QuoteBean {
            @Override
            public String toString() {
                return "QuoteBean{" +
                        "USD=" + USD +
                        '}';
            }

            /**
             * USD : {"price":8721.17308305,"volume_24h":1.94308068959716E10,"percent_change_1h":0.985353,"percent_change_24h":-0.050818,"percent_change_7d":-4.21443,"market_cap":1.574046331021591E11,"last_updated":"2019-11-15T09:14:36.000Z"}
             */



            private USDBean USD;

            public USDBean getUSD() {
                return USD;
            }

            public void setUSD(USDBean USD) {
                this.USD = USD;
            }

            public static class USDBean {
                @Override
                public String toString() {
                    return "USDBean{" +
                            "price=" + price +
                            ", volume_24h=" + volume_24h +
                            ", percent_change_1h=" + percent_change_1h +
                            ", percent_change_24h=" + percent_change_24h +
                            ", percent_change_7d=" + percent_change_7d +
                            ", market_cap=" + market_cap +
                            ", last_updated='" + last_updated + '\'' +
                            '}';
                }

                /**
                 * price : 8721.17308305
                 * volume_24h : 1.94308068959716E10
                 * percent_change_1h : 0.985353
                 * percent_change_24h : -0.050818
                 * percent_change_7d : -4.21443
                 * market_cap : 1.574046331021591E11
                 * last_updated : 2019-11-15T09:14:36.000Z
                 */



                private double price;
                private double volume_24h;
                private double percent_change_1h;
                private double percent_change_24h;
                private double percent_change_7d;
                private double market_cap;
                private String last_updated;

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public double getVolume_24h() {
                    return volume_24h;
                }

                public void setVolume_24h(double volume_24h) {
                    this.volume_24h = volume_24h;
                }

                public double getPercent_change_1h() {
                    return percent_change_1h;
                }

                public void setPercent_change_1h(double percent_change_1h) {
                    this.percent_change_1h = percent_change_1h;
                }

                public double getPercent_change_24h() {
                    return percent_change_24h;
                }

                public void setPercent_change_24h(double percent_change_24h) {
                    this.percent_change_24h = percent_change_24h;
                }

                public double getPercent_change_7d() {
                    return percent_change_7d;
                }

                public void setPercent_change_7d(double percent_change_7d) {
                    this.percent_change_7d = percent_change_7d;
                }

                public double getMarket_cap() {
                    return market_cap;
                }

                public void setMarket_cap(double market_cap) {
                    this.market_cap = market_cap;
                }

                public String getLast_updated() {
                    return last_updated;
                }

                public void setLast_updated(String last_updated) {
                    this.last_updated = last_updated;
                }
            }
        }
    }
}
