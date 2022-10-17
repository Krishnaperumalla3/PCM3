/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { Pipe, PipeTransform } from '@angular/core';

declare var require: any;
const orderby = require('lodash.orderby');

/**
 * Utility class to not hardcode sort directions
 */
export class SortOptions {
  /**
   * Static property to defined ASC and DESC values
   * to avoid hardcoding and repeating
   * replaces string enums
   */
  static direction: {
    ASC: string,
    DESC: string
  } = {
    ASC: 'ASC',
    DESC: 'DESC'
  };
}


/**
 * Pipe used to sort arrays by using lodash
 * Takes array and array of 2 strings(parameters), key and direction
 * direction must be either ASC or DESC
 */
@Pipe({
  name: 'arraySort'
})
export class ArraySortPipe implements PipeTransform {

  transform(array: Array<{}>, args: string[]): Array<string> | Array<{}> {

    array = array || [];

    if (typeof args === 'undefined' || args.length !== 2) {
      return array;
    }

    const [key, direction] = args;

    if (direction !== SortOptions.direction.ASC && direction !== SortOptions.direction.DESC) {
      return array;
    }

    // if there is no key we assume item is of string type
    return orderby(array, (item: {} | string) => item.hasOwnProperty(key) ? item[key] : item, direction.toLowerCase());
  }
}

/**
 * Pipe used to filter array, takes input array and
 * array of 2 arguments, key of object and search term
 * if key does not exist, pipe assumes the item is string
 */
@Pipe({
  name: 'arrayFilter'
})
export class ArrayFilterPipe implements PipeTransform {

  transform(array: Array<{}>, args: string[]): Array<string> | Array<{}> {

    array = array || [];

    if (typeof args === 'undefined' || args.length !== 2 ) {
      return array;
    }

    const [key, searchTerm] = args;

    if (searchTerm.trim() === '') {
      return array;
    }

    return array.filter((item: {}) => item[key].toString().toLowerCase().search(searchTerm.toLowerCase().trim()) >= 0);
  }
}
