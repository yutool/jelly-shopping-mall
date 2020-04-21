class Logger {
    private NONE = 0;
    private ERROR = 1;
    private WARN = 2;
    private INFO = 3;
    private DEBUG = 4;
    private TYPE = this.INFO;
    
    public error(page: string, msg: string) {
        if (this.TYPE >= this.ERROR) {
            console.log(`${this.getdate()} ERROR --- [ ${page} ]: `, msg);
        }
    }
    public warn(page: string, msg: string) {
        if (this.TYPE >= this.WARN) {
            console.log(`${this.getdate()} WARN --- [ ${page} ]: `, msg);
        }
    }
    public info(page: string, msg: string) {
        if (this.TYPE >= this.INFO) {
            console.log(`${this.getdate()} INFO --- [ ${page} ]: `, msg);
        }
    }
    public debug(page: string, msg: string) {
        if (this.TYPE >= this.INFO) {
            console.log(`${this.getdate()} DEBUG --- [ ${page} ]: `, msg);
        }
    }
    public getdate() {
        const date = new Date();
        return date.getFullYear() + '-' +
            (date.getMonth() + 1) + '-' +
            date.getDate() + ' ' +
            date.getHours() + ':' +
            date.getMinutes() + ':' +
            date.getSeconds();
    }
}

export default new Logger();
