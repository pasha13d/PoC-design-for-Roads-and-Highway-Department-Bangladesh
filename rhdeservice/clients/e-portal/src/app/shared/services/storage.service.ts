import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class StorageService {
    private storage: Storage;

    constructor() {
        this.storage = localStorage;
    }

    public save(key: any, value: any) {
        value = JSON.stringify(value);
        this.storage.setItem(key, value);
    }

    public read(key: any): any {
        const value = this.storage.getItem(key);
        return JSON.parse(value);
    }

    public remove(key: any) {
        return this.storage.removeItem(key);
    }

    public clear() {
        this.storage.clear();
    }
}

export enum StorageKey {
    AUTH_TOKEN = 'AUTH_TOKEN',
    PAGE_NUMBER = 'PAGE_NUMBER'
}
