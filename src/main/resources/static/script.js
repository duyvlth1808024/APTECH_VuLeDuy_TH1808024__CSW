function convertToMoney(number) {
    return number.toLocaleString('vn-VN', { style: 'currency', currency: 'VND' });
}

function removeCurrency(str) {
    return str.replace(/\D+/g, '').replace('₫','').replace(',','')
}

var bankingService = {
    createAccount(data, fn) {
        axios
            .post('/api/v1/account/create', data)
            .then(res => fn(res))
            .catch(error => console.log(error));
    },
    createPartner(data, fn) {
        axios
            .post('/api/v1/partner/create', data)
            .then(res => fn(res))
            .catch(error => console.log(error));
    },
    createConnect(data, fn) {
        axios
            .post('/api/v1/partner/connect-banking', data)
            .then(res => fn(res))
            .catch(error => console.log(error));
    },
    getAccountBankingPartner(data, fn) {
        axios
            .post('/api/v1/account', data)
            .then(res => fn(res))
            .catch(error => console.log(error));
    },
    payment(data, fn) {
        axios
            .post('/api/v1/payment', data)
            .then(res => fn(res))
            .catch(error => console.log(error))
    },
    findTransaction(type, data, fn) {
        axios
            .post('/api/v1/payment/' + type, data)
            .then(res => fn(res))
            .catch(error => console.log(error))
    }
};

var Selection = Vue.extend({
    template: '#selection'
});

var SignUpAccount = Vue.extend({
    template: '#sign-up-account',
    data: function() {
        return {
            error: '',
            account: {
                accountNumber: '',
                pinNumber: '',
            },
        };
    },
    methods: {
        onChangeAccountNumber: function(e) {
            this.account.accountNumber = e.target.value.replace(/\D+/g, '');
        },
        createAccount: function() {
            this.error = "";
            if(this.account.accountNumber.length !== 8) {
                this.error = "Mã số tài khoản cần có độ dài bằng 8."
                return;
            }
            if(this.account.pinNumber.length !== 6) {
                this.error = "Mã PIN cần có độ dài bằng 6.";
                return;
            }
            bankingService.createAccount(this.account, res => {
                if(res.data.error && res.data.error.length > 0) {
                    this.error = res.data.error;
                    return;
                }
                router.push('/');
            });
        },
    },
});

var SignUpPartner = Vue.extend({
    template: '#sign-up-partner',
    data: function() {
        return {
            error: '',
            partner: {
                accountNumber: '',
                password: '',
            },
        };
    },
    methods: {
        onChangeAccountNumber: function(e) {
            this.partner.accountNumber = e.target.value.replace(/\D+/g, '');
        },
        createPartner: function() {
            this.error = "";
            if(this.partner.accountNumber.length !== 8) {
                this.error = "Mã số tài khoản cần có độ dài bằng 8."
                return;
            }
            if(this.partner.password.length <= 6) {
                this.error = "Mật khẩu cần có độ dài tối thiểu 6 ký tự.";
                return;
            }
            bankingService.createPartner(this.partner, res => {
                if(res.data.error && res.data.error.length > 0) {
                    this.error = res.data.error;
                    return;
                }
                router.push('/');
            });
        },
    },
});

var ConnectBanking = Vue.extend({
    template: '#partner-connect-banking',
    data: function() {
        return {
            error: '',
            dto: {
                step: 1,
                accountNumber: '',
                accountPINNumber: '',
                partnerAccountNumber: '',
                partnerAccountPassword: ''
            },
        };
    },
    methods: {
        createConnect: function() {
            bankingService.createConnect(this.dto, (res) => {
                this.error = "";
                if(res.data.error && res.data.error.length > 0) {
                    this.error = res.data.error;
                    return;
                }
                if(this.dto.step === 1) {
                    this.dto.step = 2;
                    return;
                }
                router.push('/');
            });
        },
    },
});

var Payment = Vue.extend({
    template: '#payment',
    data: function () {
        return {
            error: '',
            step: 1,
            partner: {
                accountNumber: '',
                password: '',
            },
            accounts: [],
            feeTransfer: '0',
            dto: {
                accountNumber: '',
                accountPaymentNumber: '',
                accountPaymentPIN: '',
                billCode: '',
                amount: '',
                typeTransaction: 'TRANSFER',
            },
        };
    },
    methods: {
        onChangeAmount: function(e) {
            const amount = removeCurrency(e.target.value);
            if(!amount || amount <= 0 || amount.length === 0) {
                this.feeTransfer = 0;
            }
            if(amount >0 && amount <= 100000) {
                this.feeTransfer = convertToMoney(10000);
            }
            if(amount > 100000 && amount <= 500000) {
                this.feeTransfer = convertToMoney(amount/100*2);
            }
            if(amount > 500000 && amount <= 1000000) {
                this.feeTransfer = convertToMoney(amount/100*1.5);
            }
            if(amount > 1000000 && amount <= 5000000) {
                this.feeTransfer = convertToMoney(amount/100);
            }
            if(amount > 5000000) {
                this.feeTransfer = convertToMoney(amount/200);
            }
            this.dto.amount = convertToMoney(+amount);
        },
      getAccountBankingPartner: function() {
        bankingService.getAccountBankingPartner(this.partner, (res) => {
           this.error = '';
           if(res.data.error) {
               this.error = res.data.error;
               return;
           }
           this.accounts = res.data;
            if(res.data[0]) {
                this.dto.accountNumber = res.data[0].accountNumber;
            }
           this.step = 2;
        });
      },
    submitPayment: function() {
            this.dto.amount = removeCurrency(this.dto.amount);
        bankingService.payment(this.dto, (res) => {
            this.error = '';
            if(res.data.error) {
                this.error = res.data.error;
                return;
            }
            router.push('/');
        });
      },
    },
});

var TransactionHistory = Vue.extend({
    template: '#transaction-history',
    data: function() {
        return {
            error: '',
            finish: false,
            findType: 'partner',
            account: {
                accountNumber: '',
                pinNumber: '',
            },
            partner: {
                accountNumber: '',
                password: '',
            },
            startDate: '',
            endDate: '',
            transactions: [],
        };
    },
    methods: {
        find: function () {
            this.transactions = [];
            this.finish = false;
            this.error = '';
            if(this.startDate.length <= 0) {
                this.error = 'Vui lòng điền ngày bắt đầu';
                return;
            }
            if(this.endDate.length <= 0) {
                this.error = 'Vui lòng điền ngày kết thúc';
                return;
            }
            let data = {
                startDate: this.startDate,
                endDate: this.endDate,
            };
            if(this.findType === 'partner') {
                data.accountNumber = this.partner.accountNumber;
                data.password = this.partner.password;
            } else {
                data.accountNumber = this.account.accountNumber;
                data.pinNumber = this.account.pinNumber;
            }
            bankingService.findTransaction(this.findType, data, (res) => {
               if(res.data.error && res.data.error.length > 0) {
                   this.error = res.data.error;
                   return;
               }
               this.finish = true;
               this.transactions = res.data;
            });
        },
        formatMoney: function(money) {
            return convertToMoney(money);
        },
    },
})

var router = new VueRouter({
    routes: [
        { path: '/', component: Selection },
        { path: '/account/create', component: SignUpAccount },
        { path: '/partner/create', component: SignUpPartner },
        { path: '/partner/connect-banking', component: ConnectBanking },
        { path: '/payment', component: Payment },
        { path: '/transaction-history', component: TransactionHistory }
    ],
});
new Vue({
    router
}).$mount('#app');